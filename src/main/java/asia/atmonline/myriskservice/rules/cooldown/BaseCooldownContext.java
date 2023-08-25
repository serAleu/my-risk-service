package asia.atmonline.myriskservice.rules.cooldown;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseCooldownContext extends BaseRuleContext {

  public BaseCooldownContext() {
    super(new RiskResponseJpaEntity());
  }
}
