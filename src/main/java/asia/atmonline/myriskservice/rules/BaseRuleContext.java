package asia.atmonline.myriskservice.rules;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class BaseRuleContext {

  private RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity;

}
