package asia.atmonline.myriskservice.services.seon;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.SeonFraudResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.external_responses.SeonFraudResponseJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.rules.seon.phone.SeonPhoneRule;
import asia.atmonline.myriskservice.rules.seon.phone.SeonPhoneRuleContext;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import asia.atmonline.myriskservice.services.seon.property.SeonPropertyManager;
import asia.atmonline.myriskservice.utils.JsonUtils;
import asia.atmonline.myriskservice.web.seon.client.SeonFraudFeignClient;
import asia.atmonline.myriskservice.web.seon.dto.Config;
import asia.atmonline.myriskservice.web.seon.dto.ConfigDetail;
import asia.atmonline.myriskservice.web.seon.dto.FraudRequest;
import asia.atmonline.myriskservice.web.seon.dto.FraudResponse;
import asia.atmonline.myriskservice.web.seon.dto.SeonResponseError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeonFraudChecksService implements BaseRiskChecksService {

  private final SeonFraudFeignClient client;
  private final ObjectMapper mapper;
  private final SeonFraudResponseJpaRepository seonFraudResponseJpaRepository;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final SeonPhoneRule seonPhoneRule;
  private final SeonPropertyManager seonPropertyManager;

  @Override
  public RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request) {
    Optional<CreditApplication> application = creditApplicationJpaRepository.findById(request.getApplicationId());
    if (application.isPresent() && application.get().getBorrower() != null) {
      boolean isNewSeonData = false;
      Optional<SeonFraudResponseRiskJpaEntity> seonFraudOldResponseJpaEntityOptional = seonFraudResponseJpaRepository
          .findTop1ByBorrowerIdAndCreatedAtGreaterThanAndSuccessOrderByCreatedAtDesc(application.get().getBorrower().getId(),
              LocalDateTime.now().minus(seonPropertyManager.getSeonFraudRequestLimit(), ChronoUnit.DAYS), true);
      SeonFraudResponseRiskJpaEntity currentResponse;
      if (seonFraudOldResponseJpaEntityOptional.isEmpty() || isNeedToGetNewSeonInfo(application.get(), seonFraudOldResponseJpaEntityOptional.get())) {
        currentResponse = getFraudData(application.get());
        isNewSeonData = true;
      } else {
        currentResponse = seonFraudOldResponseJpaEntityOptional.get();
      }
      if (currentResponse != null) {
        seonFraudResponseJpaRepository.save(currentResponse);
      }
      return seonPhoneRule.execute(new SeonPhoneRuleContext(application.get().getId(), currentResponse, isNewSeonData,
          seonPropertyManager.getSeonFraudPhoneStopFactorEnable()));
    }
    return new RiskResponseRiskJpaEntity();
  }

  @Transactional
  public SeonFraudResponseRiskJpaEntity getFraudData(CreditApplication application) {
    boolean seonEmailEnabled =
        seonPropertyManager.getSeonFraudEmailEnable() && StringUtils.isNoneBlank(application.getBorrower().getPersonalData().getEmail());

    Config config = Config.builder().deviceFingerPrinting(seonPropertyManager.getSeonFraudFingerprintEnable())
        .emailApi(seonEmailEnabled)
        .phoneApi(seonPropertyManager.getSeonFraudPhoneEnable())
        .email(new ConfigDetail().setInclude("flags,history,id").setVersion("v" + seonPropertyManager.getSeonFraudEmailApiVersion()))
        .phone(new ConfigDetail().setInclude("flags,history,id").setVersion("v" + seonPropertyManager.getSeonFraudPhoneApiVersion()))
        .ip(new ConfigDetail().setInclude("flags,history,id").setVersion("v" + seonPropertyManager.getSeonFraudIpApiVersion())).build();

    FraudRequest fraudRequest = FraudRequest.builder().config(config)
        .email(application.getBorrower().getPersonalData().getEmail())
        .userCreated(application.getBorrower().getCreatedAt().atZone(ZoneId.systemDefault()).toEpochSecond())
        .ip(application.getIpAddress())
        .phoneNumber(formatPhone(application.getBorrower().getPersonalData().getMobilePhone()))
//        .session(application.getBorrower().getAttributes().get(SEON_SESSION))
//        .sessionId(application.getBorrower().getAttributes().get(SEON_SESSION_ID))
        .session("TEST")
        .sessionId("TEST")
        .userfullname(application.getBorrower().getEmploymentData().getEmployerName())
        .userDob(application.getBorrower().getPersonalData().getBirthDate())
        .userId(application.getBorrower().getId()).userCountry("MY")
        .customFields(Collections.emptyMap()).build();

    SeonFraudResponseRiskJpaEntity seonFraudResponseJpaEntity;
    try {
      Object response = client.getSeonFraud(seonPropertyManager.getSeonFraudLicenseKey(), fraudRequest, seonPropertyManager.getSeonFraudTimeout());
      seonFraudResponseJpaEntity = extractSeonFraudResponseJpaEntity(response, application);
    } catch (Exception e) {
      log.error("Failed to get Seon fraud Response with Borrower id: {}, Application id: {}", application.getBorrower().getId(), application.getId(),
          e);
      seonFraudResponseJpaEntity = SeonFraudResponseRiskJpaEntity.builder()
          .borrowerId(application.getBorrower().getId())
          .creditApplicationId(application.getId())
          .phone(application.getBorrower().getPersonalData().getMobilePhone())
          .fraudResponse(new FraudResponse(new SeonResponseError(e.getMessage())))
          .build();
    }
    seonFraudResponseJpaEntity.setPhoneRequest(seonPropertyManager.getSeonFraudPhoneEnable());
    seonFraudResponseJpaEntity.setEmailRequest(seonEmailEnabled);
    seonFraudResponseJpaEntity.setDeviceFingerprintRequest(seonPropertyManager.getSeonFraudFingerprintEnable());
    seonFraudResponseJpaEntity.setResponse(seonFraudResponseJpaEntity.getFraudResponse().toString());
    return seonFraudResponseJpaEntity;
  }

  private SeonFraudResponseRiskJpaEntity extractSeonFraudResponseJpaEntity(Object response, CreditApplication application)
      throws JsonProcessingException {
    String responseString = mapper.writeValueAsString(response);
    FraudResponse fraudResponse = JsonUtils.decodeDefault(responseString, FraudResponse.class);
    SeonFraudResponseRiskJpaEntity seonFraudResponseJpaEntity = new SeonFraudResponseRiskJpaEntity().setCreditApplicationId(application.getId())
        .setBorrowerId(application.getBorrower().getId())
        .setPhone(application.getBorrower().getPersonalData().getMobilePhone())
        .setFraudResponse(fraudResponse)
        .setSuccess(true);
    seonFraudResponseJpaEntity.setOriginalResponse(responseString);
    return seonFraudResponseJpaEntity;
  }

  private boolean isNeedToGetNewSeonInfo(CreditApplication application, SeonFraudResponseRiskJpaEntity seonFraudOldResponseJpaEntity) {
    if (BooleanUtils.isNotTrue(seonFraudOldResponseJpaEntity.getSuccess())) {
      return true;
    }
    boolean[] lastSettings = new boolean[]{BooleanUtils.isTrue(seonFraudOldResponseJpaEntity.getPhoneRequest()),
        BooleanUtils.isTrue(seonFraudOldResponseJpaEntity.getEmailRequest()),
        BooleanUtils.isTrue(seonFraudOldResponseJpaEntity.getDeviceFingerprintRequest())};
    boolean[] currentSettings = new boolean[]{seonPropertyManager.getSeonFraudPhoneEnable(),
        seonPropertyManager.getSeonFraudEmailEnable() && StringUtils.isNoneBlank(application.getBorrower().getPersonalData().getEmail()),
        seonPropertyManager.getSeonFraudFingerprintEnable()};
    for (int index = 0; index < lastSettings.length; index++) {
      if (!lastSettings[index] && currentSettings[index]) {
        return true;
      }
    }
    return false;
  }

  private String formatPhone(final String borrowerPhone) {
    return RegExUtils.replaceFirst(borrowerPhone, "0", "94");
  }
}

