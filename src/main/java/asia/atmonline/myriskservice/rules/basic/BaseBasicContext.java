package asia.atmonline.myriskservice.rules.basic;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseBasicContext extends BaseRuleContext {

  public final Boolean isFinalChecks;

  public BaseBasicContext(RiskResponseJpaEntity response, Boolean isFinalChecks) {
    super(response);
    this.isFinalChecks = isFinalChecks;
  }
}
