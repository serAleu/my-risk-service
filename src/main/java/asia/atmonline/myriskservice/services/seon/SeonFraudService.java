package asia.atmonline.myriskservice.services.seon;

import static asia.atmonline.myriskservice.enums.FinalDecision.APPROVE;
import static asia.atmonline.myriskservice.enums.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.GroupOfChecks.SEON;
import static asia.atmonline.myriskservice.enums.RejectionReasonCode.SEONPHONE;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.requests.impl.SeonFraudRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.responses.impl.SeonFraudResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.SeonFraudResponseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.SeonFraudRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.seon.SeonFraudSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import asia.atmonline.myriskservice.utils.JsonUtils;
import asia.atmonline.myriskservice.web.seon.client.SeonFraudFeignClient;
import asia.atmonline.myriskservice.web.seon.dto.AccountDetails;
import asia.atmonline.myriskservice.web.seon.dto.Config;
import asia.atmonline.myriskservice.web.seon.dto.ConfigDetail;
import asia.atmonline.myriskservice.web.seon.dto.FraudRequest;
import asia.atmonline.myriskservice.web.seon.dto.FraudResponse;
import asia.atmonline.myriskservice.web.seon.dto.SeonResponseError;
import asia.atmonline.myriskservice.web.seon.dto.social.Facebook;
import asia.atmonline.myriskservice.web.seon.dto.social.Google;
import asia.atmonline.myriskservice.web.seon.dto.social.Instagram;
import asia.atmonline.myriskservice.web.seon.dto.social.Kakao;
import asia.atmonline.myriskservice.web.seon.dto.social.Line;
import asia.atmonline.myriskservice.web.seon.dto.social.Microsoft;
import asia.atmonline.myriskservice.web.seon.dto.social.Ok;
import asia.atmonline.myriskservice.web.seon.dto.social.Skype;
import asia.atmonline.myriskservice.web.seon.dto.social.Snapchat;
import asia.atmonline.myriskservice.web.seon.dto.social.Telegram;
import asia.atmonline.myriskservice.web.seon.dto.social.Twitter;
import asia.atmonline.myriskservice.web.seon.dto.social.Viber;
import asia.atmonline.myriskservice.web.seon.dto.social.Whatsapp;
import asia.atmonline.myriskservice.web.seon.dto.social.Zalo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class SeonFraudService extends BaseChecksService<SeonFraudRequest, SeonFraudRequestJpaEntity, SeonFraudResponseJpaEntity> {

  private final SeonFraudFeignClient client;
  private final ObjectMapper mapper;
  private final SeonFraudResponseJpaRepository seonFraudResponseJpaRepository;

  public SeonFraudService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories, SeonFraudFeignClient client,
      ObjectMapper mapper, SeonFraudResponseJpaRepository seonFraudResponseJpaRepository) {
    super(repositories);
    this.client = client;
    this.mapper = mapper;
    this.seonFraudResponseJpaRepository = seonFraudResponseJpaRepository;
  }

  @Override
  public RiskResponse<SeonFraudSqsProducer> process(SeonFraudRequest request) {
    RiskResponse<SeonFraudSqsProducer> riskResponse = new RiskResponse<>();
    riskResponse.setApplicationId(riskResponse.getApplicationId());
    riskResponse.setDecision(APPROVE);
    riskResponse.setCheck(request.getCheck());
    Optional<SeonFraudResponseJpaEntity> seonFraudOldResponseJpaEntityOptional = seonFraudResponseJpaRepository
        .findTop1ByBorrowerIdAndCreatedAtGreaterThanAndSuccessOrderByCreatedAtDesc(request.getBorrowerId(),
            LocalDateTime.now().minus(request.getSeonFraudRequestsLimit(), ChronoUnit.DAYS), true);
    SeonFraudResponseJpaEntity currentResponse;
    if(seonFraudOldResponseJpaEntityOptional.isEmpty() || isNeedToGetNewSeonInfo(request, seonFraudOldResponseJpaEntityOptional.get())) {
      currentResponse = getFraudData(request);
      if(request.getSeonFraudPhoneStopFactorEnable() && currentResponse != null && currentResponse.getSuccess()){
        AccountDetails accountDetails = currentResponse.getResponse().getData().getPhoneDetails().getAccountDetails();
        if(accountDetails != null && checkRegistrations(accountDetails)) {
          riskResponse.setDecision(REJECT);
          riskResponse.setRejectionReasonCode(SEONPHONE);
        }
      }
    } else {
      currentResponse = seonFraudOldResponseJpaEntityOptional.get();
    }
    if(currentResponse != null) {
      seonFraudResponseJpaRepository.save(currentResponse);
    }
    return riskResponse;
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
    return new SeonFraudRequestJpaEntity();
  }

  @Override
  public SeonFraudResponseJpaEntity getResponseEntity(RiskResponse<? extends BaseSqsProducer> response) {
    return new SeonFraudResponseJpaEntity();
  }

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
          .createdAt(now)
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
    SeonFraudResponseJpaEntity seonFraudResponseJpaEntity = new SeonFraudResponseJpaEntity(request.getApplicationId(), request.getBorrowerId(),
        request.getMobilePhone(), fraudResponse, true);
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

  private boolean checkRegistrations(AccountDetails accountDetails) {
    Facebook facebook = accountDetails.getFacebook();
    Google google = accountDetails.getGoogle();
    Instagram instagram = accountDetails.getInstagram();
    Telegram telegram = accountDetails.getTelegram();
    Twitter twitter = accountDetails.getTwitter();
    Viber viber = accountDetails.getViber();
    Whatsapp whatsapp = accountDetails.getWhatsapp();

    boolean facebookRegistered = facebook != null && facebook.isRegistered();
    boolean googleRegistered = google != null && google.isRegistered();
    boolean instagramRegistered = instagram != null && instagram.isRegistered();
    boolean telegramRegistered = telegram != null && telegram.isRegistered();
    boolean twitterRegistered = twitter != null && twitter.isRegistered();
    boolean viberRegistered = viber != null && viber.isRegistered();
    boolean whatsappRegistered = whatsapp != null && whatsapp.isRegistered();

    Zalo zalo = accountDetails.getZalo();
    boolean zaloRegistered = Objects.nonNull(zalo) && zalo.isRegistered();

    Ok ok = accountDetails.getOk();
    boolean okRegistered = Objects.nonNull(ok) && ok.isRegistered();

    Line line = accountDetails.getLine();
    boolean lineRegistered = Objects.nonNull(line) && line.isRegistered();

    Microsoft microsoft = accountDetails.getMicrosoft();
    boolean microsoftRegistered = Objects.nonNull(microsoft) && microsoft.isRegistered();

    Snapchat snapchat = accountDetails.getSnapchat();
    boolean snapchatRegistered = Objects.nonNull(snapchat) && snapchat.isRegistered();

    Skype skype = accountDetails.getSkype();
    boolean skypeRegistered = Objects.nonNull(skype) && skype.isRegistered();

    Kakao kakao = accountDetails.getKakao();
    boolean kakaoRegistered = Objects.nonNull(kakao) && kakao.isRegistered();

    return !facebookRegistered && !googleRegistered && !instagramRegistered && !telegramRegistered
        && !twitterRegistered && !viberRegistered && !whatsappRegistered && !zaloRegistered && !okRegistered && !lineRegistered
        && !microsoftRegistered && !snapchatRegistered && !skypeRegistered && !kakaoRegistered;
  }

  private String formatPhone(final String borrowerPhone) {
    return RegExUtils.replaceFirst(borrowerPhone, "0", "94");
  }
}