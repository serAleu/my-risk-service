package asia.atmonline.myriskservice.rules.basic;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRuleContext;

public abstract class BaseBasicContext extends BaseRuleContext {

  public BaseBasicContext() {
    super(new RiskResponseJpaEntity<BasicSqsProducer>());
  }
}
