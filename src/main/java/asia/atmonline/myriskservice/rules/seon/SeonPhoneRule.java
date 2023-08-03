package asia.atmonline.myriskservice.rules.seon;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;

public class SeonPhoneRule extends BaseRule {

  @Override
  public void execute(RiskResponse<? extends BaseSqsProducer> response) {

  }
}
