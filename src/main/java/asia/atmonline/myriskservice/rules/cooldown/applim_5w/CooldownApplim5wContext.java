package asia.atmonline.myriskservice.rules.cooldown.applim_5w;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRuleContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooldownApplim5wContext extends BaseRuleContext {

  public CooldownApplim5wContext() {
    super(new RiskResponseJpaEntity<CooldownSqsProducer>());
  }
}
