package asia.atmonline.myriskservice.rules.cooldown;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseCooldownContext extends BaseRuleContext {

  public BaseCooldownContext() {
    super(new RiskResponseRiskJpaEntity());
  }
}
