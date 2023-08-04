package asia.atmonline.myriskservice.rules.seon;

import static asia.atmonline.myriskservice.enums.FinalDecision.APPROVE;
import static asia.atmonline.myriskservice.enums.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.GroupOfChecks.SEON;
import static asia.atmonline.myriskservice.enums.RejectionReasonCode.SEONPHONE;

import asia.atmonline.myriskservice.data.entity.responses.impl.SeonFraudResponseJpaEntity;
import asia.atmonline.myriskservice.messages.request.impl.SeonFraudRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.seon.SeonFraudSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import asia.atmonline.myriskservice.web.seon.dto.AccountDetails;
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
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class SeonPhoneRule extends BaseRule<SeonRuleContext> {

  @Override
  public RiskResponse<SeonFraudSqsProducer> execute(SeonRuleContext context) {
    SeonFraudResponseJpaEntity entity = context.getEntity();
    SeonFraudRequest request = context.getRequest();
    RiskResponse<SeonFraudSqsProducer> riskResponse = new RiskResponse<>();
    riskResponse.setApplicationId(entity.getApplicationId());
    riskResponse.setDecision(APPROVE);
    riskResponse.setCheck(SEON);
    if(context.getIsNewSeonData() && request.getSeonFraudPhoneStopFactorEnable() && entity.getSuccess()){
      AccountDetails accountDetails = entity.getResponse().getData().getPhoneDetails().getAccountDetails();
      if(accountDetails != null && checkRegistrations(accountDetails)) {
        riskResponse.setDecision(REJECT);
        riskResponse.setRejectionReasonCode(SEONPHONE);
      }
    }
    return riskResponse;
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
}
