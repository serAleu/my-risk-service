package asia.atmonline.myriskservice.rules.cooldown.active_app;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRuleContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooldownActiveAppContext extends BaseRuleContext {

  public CooldownActiveAppContext() {
    super(new RiskResponseJpaEntity<CooldownSqsProducer>());
  }
}
