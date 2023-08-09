package asia.atmonline.myriskservice.services.seon;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.SEON;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.SeonFraudRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.SeonFraudResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.risk.responses.SeonFraudResponseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.SeonFraudRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.seon.SeonFraudSqsProducer;
import asia.atmonline.myriskservice.rules.seon.phone.SeonPhoneRule;
import asia.atmonline.myriskservice.rules.seon.phone.SeonPhoneRuleContext;
import asia.atmonline.myriskservice.services.BaseChecksService;
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
public class SeonFraudService extends BaseChecksService<SeonFraudRequest, SeonFraudRequestJpaEntity> {

  private final SeonFraudFeignClient client;
  private final ObjectMapper mapper;
  private final SeonFraudResponseJpaRepository seonFraudResponseJpaRepository;
  private final SeonPhoneRule seonPhoneRule;

  public SeonFraudService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories, SeonFraudFeignClient client,
      ObjectMapper mapper, SeonFraudResponseJpaRepository seonFraudResponseJpaRepository, SeonPhoneRule seonPhoneRule) {
    super(repositories);
    this.client = client;
    this.mapper = mapper;
    this.seonFraudResponseJpaRepository = seonFraudResponseJpaRepository;
    this.seonPhoneRule = seonPhoneRule;
  }

  @Override
  public RiskResponseJpaEntity<SeonFraudSqsProducer> process(SeonFraudRequest request) {
    boolean isNewSeonData = false;
    Optional<SeonFraudResponseJpaEntity> seonFraudOldResponseJpaEntityOptional = seonFraudResponseJpaRepository
        .findTop1ByBorrowerIdAndCreatedAtGreaterThanAndSuccessOrderByCreatedAtDesc(request.getBorrowerId(),
            LocalDateTime.now().minus(request.getSeonFraudRequestsLimit(), ChronoUnit.DAYS), true);
    SeonFraudResponseJpaEntity currentResponse;
    if(seonFraudOldResponseJpaEntityOptional.isEmpty() || isNeedToGetNewSeonInfo(request, seonFraudOldResponseJpaEntityOptional.get())) {
      currentResponse = getFraudData(request);
      isNewSeonData = true;
    } else {
      currentResponse = seonFraudOldResponseJpaEntityOptional.get();
    }
    if(currentResponse != null) {
      seonFraudResponseJpaRepository.save(currentResponse);
    }
    return seonPhoneRule.execute(new SeonPhoneRuleContext(request, currentResponse, isNewSeonData));
  }

  @Override
  public boolean accept(SeonFraudRequest request) {
    return request != null
        && request.getCheck() != null
        && SEON.equals(request.getCheck())
        && (request.getSeonFraudPhoneEnable() || request.getSeonFraudEmailEnable() || request.getSeonFraudFingerprintEnable());
  }

  @Override
  public SeonFraudRequestJpaEntity getRequestEntity(SeonFraudRequest request) {
    return new SeonFraudRequestJpaEntity().setApplicationId(request.getApplicationId())
        .setBorrowerId(request.getBorrowerId())
        .setOriginalRequest(request.toString());
  }

//  @Override
//  public SeonFraudResponseJpaEntity getResponseEntity(RiskResponseJpaEntity<? extends BaseSqsProducer> response) {
//    return new SeonFraudResponseJpaEntity();
//  }

  @Transactional
  public SeonFraudResponseJpaEntity getFraudData(SeonFraudRequest request) {
    boolean seonEmailEnabled = request.getSeonFraudEmailEnable() && StringUtils.isNoneBlank(request.getEmail());

    Config config = Config.builder().deviceFingerPrinting(request.getSeonFraudFingerprintEnable())
        .emailApi(seonEmailEnabled)
        .phoneApi(request.getSeonFraudPhoneEnable())
        .email(new ConfigDetail().setInclude("flags,history,id").setVersion("v" + request.getSeonFraudEmailApiVersion()))
        .phone(new ConfigDetail().setInclude("flags,history,id").setVersion("v" + request.getSeonFraudPhoneApiVersion()))
        .ip(new ConfigDetail().setInclude("flags,history,id").setVersion("v" + request.getSeonFraudIpApiVersion())).build();

    FraudRequest fraudRequest = FraudRequest.builder().config(config)
        .email(request.getEmail())
        .userCreated(request.getCreatedAt().atZone(ZoneId.systemDefault()).toEpochSecond())
        .ip(request.getIpAddress())
        .phoneNumber(formatPhone(request.getMobilePhone()))
        .session(request.getSession())
        .sessionId(request.getSessionId())
        .userfullname(request.getFullName())
        .userDob(request.getBirthDate())
        .userId(request.getBorrowerId()).userCountry("MY")
        .customFields(Collections.emptyMap()).build();

    SeonFraudResponseJpaEntity seonFraudResponseJpaEntity;
    LocalDateTime now = LocalDateTime.now();
    try {
      Object response = client.getSeonFraud(request.getSeonFraudLicenseKey(), fraudRequest, request.getSeonFraudTimeout());
      seonFraudResponseJpaEntity = extractSeonFraudResponseJpaEntity(response, request);
    } catch (Exception e) {
      log.error("Failed to get Seon fraud Response with Borrower id: {}, Application id: {}", request.getBorrowerId(), request.getApplicationId(), e);
      seonFraudResponseJpaEntity = SeonFraudResponseJpaEntity.builder()
          .borrowerId(request.getBorrowerId())
          .applicationId(request.getApplicationId())
          .phone(request.getMobilePhone())
          .response(new FraudResponse(new SeonResponseError(e.getMessage())))
          .build();
    }
    seonFraudResponseJpaEntity.setPhoneRequest(request.getSeonFraudPhoneEnable());
    seonFraudResponseJpaEntity.setEmailRequest(seonEmailEnabled);
    seonFraudResponseJpaEntity.setDeviceFingerprintRequest(request.getSeonFraudFingerprintEnable());
    return seonFraudResponseJpaEntity;
  }

  private SeonFraudResponseJpaEntity extractSeonFraudResponseJpaEntity(Object response, SeonFraudRequest request) throws JsonProcessingException {
    String responseString = mapper.writeValueAsString(response);
    FraudResponse fraudResponse = JsonUtils.decodeDefault(responseString, FraudResponse.class);
    SeonFraudResponseJpaEntity seonFraudResponseJpaEntity = new SeonFraudResponseJpaEntity().setApplicationId(request.getApplicationId())
        .setBorrowerId(request.getBorrowerId())
        .setPhone(request.getMobilePhone())
        .setResponse(fraudResponse)
        .setSuccess(true);
    seonFraudResponseJpaEntity.setOriginalResponse(responseString);
    return seonFraudResponseJpaEntity;
  }

  private boolean isNeedToGetNewSeonInfo(SeonFraudRequest request, SeonFraudResponseJpaEntity seonFraudOldResponseJpaEntity) {
    if (BooleanUtils.isNotTrue(seonFraudOldResponseJpaEntity.getSuccess())) {
      return true;
    }
    boolean[] lastSettings = new boolean[]{BooleanUtils.isTrue(seonFraudOldResponseJpaEntity.getPhoneRequest()),
        BooleanUtils.isTrue(seonFraudOldResponseJpaEntity.getEmailRequest()),
        BooleanUtils.isTrue(seonFraudOldResponseJpaEntity.getDeviceFingerprintRequest())};
    boolean[] currentSettings = new boolean[]{request.getSeonFraudPhoneEnable(), request.getSeonFraudEmailEnable() && StringUtils.isNoneBlank(request.getEmail()),
        request.getSeonFraudFingerprintEnable()};
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