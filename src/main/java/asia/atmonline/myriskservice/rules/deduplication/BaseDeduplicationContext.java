package asia.atmonline.myriskservice.rules.deduplication;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseDeduplicationContext extends BaseRuleContext {

  public final Boolean isFinalChecks;

  public BaseDeduplicationContext(Boolean isFinalChecks) {
    super(new RiskResponseRiskJpaEntity());
    this.isFinalChecks = isFinalChecks;
  }
}
