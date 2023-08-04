package asia.atmonline.myriskservice.rules;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;

public abstract class BaseRule<T extends RuleContext> {

  public abstract RiskResponse<? extends BaseSqsProducer> execute(T context);

}
