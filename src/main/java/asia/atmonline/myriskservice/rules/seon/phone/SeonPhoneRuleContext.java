package asia.atmonline.myriskservice.rules.seon.phone;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.seon.SeonFraudResponseRiskJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeonPhoneRuleContext extends BaseRuleContext {

  private final SeonFraudResponseRiskJpaEntity responseJpaEntity;
  private final Long applicationId;
  private final Boolean isNewSeonData;
  private final Boolean isSeonFraudPhoneStopFactorEnable;

  public SeonPhoneRuleContext(Long applicationId, SeonFraudResponseRiskJpaEntity responseJpaEntity, Boolean isNewSeonData,
      Boolean isSeonFraudPhoneStopFactorEnable) {
    super(new RiskResponseJpaEntity());
    this.responseJpaEntity = responseJpaEntity;
    this.applicationId = applicationId;
    this.isNewSeonData = isNewSeonData;
    this.isSeonFraudPhoneStopFactorEnable = isSeonFraudPhoneStopFactorEnable;
  }
}
