package asia.atmonline.myriskservice.services.seon;

import static asia.atmonline.myriskservice.enums.risk.CheckType.SEON;
import static asia.atmonline.myriskservice.enums.risk.FinalDecision.APPROVE;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.seon.SeonFraudResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.external_responses.seon.SeonFraudResponseJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
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
import org.jetbrains.annotations.NotNull;
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
  private final SeonPropertyManager seonPropertyManager;

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    Optional<CreditApplication> application = creditApplicationJpaRepository.findById(request.getApplicationId());
    if (application.isPresent() && application.get().getBorrower() != null) {
      Optional<SeonFraudResponseRiskJpaEntity> seonFraudOldResponseJpaEntityOptional = seonFraudResponseJpaRepository
          .findTop1ByBorrowerIdAndCreatedAtGreaterThanAndSuccessOrderByCreatedAtDesc(application.get().getBorrower().getId(),
              LocalDateTime.now().minus(seonPropertyManager.getSeonFraudRequestLimit(), ChronoUnit.DAYS), true);
      SeonFraudResponseRiskJpaEntity currentResponse;
      if (seonFraudOldResponseJpaEntityOptional.isEmpty() || isNeedToGetNewSeonInfo(application.get(),
          seonFraudOldResponseJpaEntityOptional.get())) {
        currentResponse = getFraudData(application.get());
      } else {
        currentResponse = seonFraudOldResponseJpaEntityOptional.get();
      }
      if (currentResponse != null) {
        seonFraudResponseJpaRepository.save(currentResponse);
      }
    }
    return getApprovedRiskResponseJpaEntity(request);
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
        .ip(new ConfigDetail().setInclude("flags,history,id").setVersion("v1.1")).build();

    String seonSession = StringUtils.isNotEmpty(application.getBorrower().getSeonSession()) ? application.getBorrower().getSeonSession() : null;
    String seonSessionId = StringUtils.isNotEmpty(application.getBorrower().getSeonSessionId()) ? application.getBorrower().getSeonSessionId() : null;
    FraudRequest fraudRequest = FraudRequest.builder().config(config)
        .email(application.getBorrower().getPersonalData().getEmail())
        .userCreated(application.getBorrower().getCreatedAt().atZone(ZoneId.systemDefault()).toEpochSecond())
        .ip(application.getIpAddress())
        .phoneNumber(formatPhone(application.getBorrower().getPersonalData().getMobilePhone()))
        .session(seonSession)
        .sessionId(seonSessionId)
//        .session(
//            "Web;13cc4856-fa42-46b5-8ff6-1d2da8b859f3;RQOkGXmovRZ0qPLxK/o6Ug==;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")
//        .sessionId("13cc4856-fa42-46b5-8ff6-1d2da8b859f3")
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

  @NotNull
  private RiskResponseJpaEntity getApprovedRiskResponseJpaEntity(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    response.setRequestId(request.getId());
    response.setApplicationId(request.getApplicationId());
    response.setCheckType(SEON);
    response.setDecision(APPROVE);
    return response;
  }
}

