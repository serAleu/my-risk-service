package asia.atmonline.myriskservice.rules.cooldown;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseCooldownContext extends BaseRuleContext {

  public BaseCooldownContext(RiskResponseJpaEntity response) {
    super(response);
  }
}
