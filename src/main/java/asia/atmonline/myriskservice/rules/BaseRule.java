package asia.atmonline.myriskservice.rules;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;

public abstract class BaseRule {

  public abstract void execute(RiskResponse<? extends BaseSqsProducer> response);

}
