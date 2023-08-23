package asia.atmonline.myriskservice.services.seon;

import static asia.atmonline.myriskservice.enums.risk.CheckType.SEON;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.SeonFraudResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.risk.responses.SeonFraudResponseJpaRepository;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
import asia.atmonline.myriskservice.producers.seon.SeonFraudSqsProducer;
import asia.atmonline.myriskservice.rules.seon.phone.SeonPhoneRule;
import asia.atmonline.myriskservice.rules.seon.phone.SeonPhoneRuleContext;
import asia.atmonline.myriskservice.services.BaseChecksService;
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
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SeonFraudChecksService extends BaseChecksService {

  private final SeonFraudFeignClient client;
  private final ObjectMapper mapper;
  private final SeonFraudResponseJpaRepository seonFraudResponseJpaRepository;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final SeonPhoneRule seonPhoneRule;
  private final ScoreSqsProducer producer;
  private final SeonPropertyManager seonPropertyManager;


  public SeonFraudChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories, SeonFraudFeignClient client,
      ObjectMapper mapper, SeonFraudResponseJpaRepository seonFraudResponseJpaRepository, CreditApplicationJpaRepository creditApplicationJpaRepository, SeonPhoneRule seonPhoneRule, ScoreSqsProducer producer,
      SeonPropertyManager seonPropertyManager) {
    super(repositories);
    this.client = client;
    this.mapper = mapper;
    this.seonFraudResponseJpaRepository = seonFraudResponseJpaRepository;
    this.creditApplicationJpaRepository = creditApplicationJpaRepository;
    this.seonPhoneRule = seonPhoneRule;
    this.producer = producer;
    this.seonPropertyManager = seonPropertyManager;
  }

  @SuppressWarnings({"unchecked"})
  public ScoreSqsProducer getProducer() {
    return producer;
  }

  @Override
  public RiskResponseJpaEntity<SeonFraudSqsProducer> process(RiskRequestJpaEntity request) {
    Optional<CreditApplication> application = creditApplicationJpaRepository.findById(request.getCreditApplicationId());
    if (application.isPresent() && application.get().getBorrower() != null) {
      boolean isNewSeonData = false;
      Optional<SeonFraudResponseJpaEntity> seonFraudOldResponseJpaEntityOptional = seonFraudResponseJpaRepository
          .findTop1ByBorrowerIdAndCreatedAtGreaterThanAndSuccessOrderByCreatedAtDesc(application.get().getBorrower().getId(),
              LocalDateTime.now().minus(seonPropertyManager.getSeonFraudRequestLimit(), ChronoUnit.DAYS), true);
      SeonFraudResponseJpaEntity currentResponse;
      if (seonFraudOldResponseJpaEntityOptional.isEmpty() || isNeedToGetNewSeonInfo(application.get(), seonFraudOldResponseJpaEntityOptional.get())) {
        currentResponse = getFraudData(application.get());
        isNewSeonData = true;
      } else {
        currentResponse = seonFraudOldResponseJpaEntityOptional.get();
      }
      if (currentResponse != null) {
        seonFraudResponseJpaRepository.save(currentResponse);
      }
      return seonPhoneRule.execute(new SeonPhoneRuleContext(application.get().getId(), currentResponse, isNewSeonData));
    }
    return new RiskResponseJpaEntity<>();
  }

  @Override
  public boolean accept(RiskRequestJpaEntity request) {
    return request != null && SEON.equals(request.getCheckType()) && request.getCreditApplicationId() != null;
  }

  @Transactional
  public SeonFraudResponseJpaEntity getFraudData(CreditApplication application) {
    boolean seonEmailEnabled = seonPropertyManager.getSeonFraudEmailEnable() && StringUtils.isNoneBlank(application.getBorrower().getPersonalData().getPdEmail());

    Config config = Config.builder().deviceFingerPrinting(seonPropertyManager.getSeonFraudFingerprintEnable())
        .emailApi(seonEmailEnabled)
        .phoneApi(seonPropertyManager.getSeonFraudPhoneEnable())
        .email(new ConfigDetail().setInclude("flags,history,id").setVersion("v" + seonPropertyManager.getSeonFraudEmailApiVersion()))
        .phone(new ConfigDetail().setInclude("flags,history,id").setVersion("v" + seonPropertyManager.getSeonFraudPhoneApiVersion()))
        .ip(new ConfigDetail().setInclude("flags,history,id").setVersion("v" + seonPropertyManager.getSeonFraudIpApiVersion())).build();

    FraudRequest fraudRequest = FraudRequest.builder().config(config)
        .email(application.getBorrower().getPersonalData().getPdEmail())
        .userCreated(application.getBorrower().getCreatedAt().atZone(ZoneId.systemDefault()).toEpochSecond())
        .ip(application.getIpAddress())
        .phoneNumber(formatPhone(application.getBorrower().getPersonalData().getMobilePhone()))
//        .session(application.getBorrower().getAttributes().get(SEON_SESSION))
//        .sessionId(application.getBorrower().getAttributes().get(SEON_SESSION_ID))
        .session("TEST")
        .sessionId("TEST")
        .userfullname(application.getBorrower().getPersonalData().getFullName())
        .userDob(application.getBorrower().getPersonalData().getBirthDate())
        .userId(application.getBorrower().getId()).userCountry("MY")
        .customFields(Collections.emptyMap()).build();

    SeonFraudResponseJpaEntity seonFraudResponseJpaEntity;
    try {
      Object response = client.getSeonFraud(seonPropertyManager.getSeonFraudLicenseKey(), fraudRequest, seonPropertyManager.getSeonFraudTimeout());
      seonFraudResponseJpaEntity = extractSeonFraudResponseJpaEntity(response, application);
    } catch (Exception e) {
      log.error("Failed to get Seon fraud Response with Borrower id: {}, Application id: {}", application.getBorrower().getId(), application.getId(),
          e);
      seonFraudResponseJpaEntity = SeonFraudResponseJpaEntity.builder()
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

  private SeonFraudResponseJpaEntity extractSeonFraudResponseJpaEntity(Object response, CreditApplication application)
      throws JsonProcessingException {
    String responseString = mapper.writeValueAsString(response);
    FraudResponse fraudResponse = JsonUtils.decodeDefault(responseString, FraudResponse.class);
    SeonFraudResponseJpaEntity seonFraudResponseJpaEntity = new SeonFraudResponseJpaEntity().setCreditApplicationId(application.getId())
        .setBorrowerId(application.getBorrower().getId())
        .setPhone(application.getBorrower().getPersonalData().getMobilePhone())
        .setFraudResponse(fraudResponse)
        .setSuccess(true);
    seonFraudResponseJpaEntity.setOriginalResponse(responseString);
    return seonFraudResponseJpaEntity;
  }

  private boolean isNeedToGetNewSeonInfo(CreditApplication application, SeonFraudResponseJpaEntity seonFraudOldResponseJpaEntity) {
    if (BooleanUtils.isNotTrue(seonFraudOldResponseJpaEntity.getSuccess())) {
      return true;
    }
    boolean[] lastSettings = new boolean[]{BooleanUtils.isTrue(seonFraudOldResponseJpaEntity.getPhoneRequest()),
        BooleanUtils.isTrue(seonFraudOldResponseJpaEntity.getEmailRequest()),
        BooleanUtils.isTrue(seonFraudOldResponseJpaEntity.getDeviceFingerprintRequest())};
    boolean[] currentSettings = new boolean[]{seonPropertyManager.getSeonFraudPhoneEnable(),
        seonPropertyManager.getSeonFraudEmailEnable() && StringUtils.isNoneBlank(application.getBorrower().getPersonalData().getPdEmail()),
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

