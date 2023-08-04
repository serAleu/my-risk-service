package asia.atmonline.myriskservice.rules.seon;

import asia.atmonline.myriskservice.data.entity.responses.impl.SeonFraudResponseJpaEntity;
import asia.atmonline.myriskservice.messages.request.impl.SeonFraudRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.seon.SeonFraudSqsProducer;
import asia.atmonline.myriskservice.rules.RuleContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class SeonRuleContext extends RuleContext {

  private SeonFraudResponseJpaEntity entity;
  private SeonFraudRequest request;
  private Boolean isNewSeonData;

  public SeonRuleContext(SeonFraudRequest request, SeonFraudResponseJpaEntity entity, Boolean isNewSeonData) {
    super(new RiskResponse<SeonFraudSqsProducer>());
    this.entity = entity;
    this.request = request;
    this.isNewSeonData = isNewSeonData;
  }
}
