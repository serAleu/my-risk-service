package asia.atmonline.myriskservice.rules.seon.phone;

import asia.atmonline.myriskservice.data.entity.risk.responses.impl.SeonFraudResponseJpaEntity;
import asia.atmonline.myriskservice.messages.request.impl.SeonFraudRequest;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.seon.SeonFraudSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRuleContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeonPhoneRuleContext extends BaseRuleContext {

  private SeonFraudResponseJpaEntity responseJpaEntity;
  private SeonFraudRequest request;
  private Boolean isNewSeonData;

  public SeonPhoneRuleContext(SeonFraudRequest request, SeonFraudResponseJpaEntity responseJpaEntity, Boolean isNewSeonData) {
    super(new RiskResponseJpaEntity<SeonFraudSqsProducer>());
    this.responseJpaEntity = responseJpaEntity;
    this.request = request;
    this.isNewSeonData = isNewSeonData;
  }
}
