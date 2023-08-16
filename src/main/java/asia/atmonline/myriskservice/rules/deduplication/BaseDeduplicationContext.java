package asia.atmonline.myriskservice.rules.deduplication;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseDeduplicationContext extends BaseRuleContext {

  public BaseDeduplicationContext() {
    super(new RiskResponseJpaEntity<DeduplicationSqsProducer>());
  }
}
