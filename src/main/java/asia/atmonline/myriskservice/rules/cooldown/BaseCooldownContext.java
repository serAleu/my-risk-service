package asia.atmonline.myriskservice.rules.cooldown;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseCooldownContext extends BaseRuleContext {

  public BaseCooldownContext() {
    super(new RiskResponseJpaEntity<CooldownSqsProducer>());
  }
}
