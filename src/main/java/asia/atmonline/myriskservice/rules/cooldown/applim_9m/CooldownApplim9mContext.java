package asia.atmonline.myriskservice.rules.cooldown.applim_9m;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRuleContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooldownApplim9mContext extends BaseRuleContext {

  public CooldownApplim9mContext() {
    super(new RiskResponseJpaEntity<CooldownSqsProducer>());
  }
}
