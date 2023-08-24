package asia.atmonline.myriskservice.rules.basic;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseBasicContext extends BaseRuleContext {

  private final Boolean isFinalChecks;

  public BaseBasicContext(Boolean isFinalChecks) {
    super(new RiskResponseJpaEntity<BasicSqsProducer>());
    this.isFinalChecks = isFinalChecks;
  }
}
