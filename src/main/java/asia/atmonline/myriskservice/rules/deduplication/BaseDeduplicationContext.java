package asia.atmonline.myriskservice.rules.deduplication;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseDeduplicationContext extends BaseRuleContext {

  public final Boolean isFinalChecks;

  public BaseDeduplicationContext(RiskResponseJpaEntity response, Boolean isFinalChecks) {
    super(response);
    this.isFinalChecks = isFinalChecks;
  }
}
