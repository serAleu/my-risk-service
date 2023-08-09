package asia.atmonline.myriskservice.rules.cooldown.applim_2d;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRuleContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooldownApplim2dContext extends BaseRuleContext {

  public CooldownApplim2dContext() {
    super(new RiskResponseJpaEntity<CooldownSqsProducer>());
  }
}
