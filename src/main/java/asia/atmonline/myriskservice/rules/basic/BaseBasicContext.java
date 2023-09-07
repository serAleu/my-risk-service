package asia.atmonline.myriskservice.rules.basic;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseBasicContext extends BaseRuleContext {

  public final Boolean isFinalChecks;

  public BaseBasicContext(Boolean isFinalChecks) {
    super(new RiskResponseRiskJpaEntity());
    this.isFinalChecks = isFinalChecks;
  }
}
