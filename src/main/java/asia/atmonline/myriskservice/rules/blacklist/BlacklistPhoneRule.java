package asia.atmonline.myriskservice.rules.blacklist;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.BL;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.blacklist.BlacklistSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import org.springframework.stereotype.Component;

@Component
public class BlacklistPhoneRule extends BaseRule<BlacklistPhoneContext> {

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<BlacklistSqsProducer> execute(BlacklistPhoneContext context) {
    RiskResponseJpaEntity<BlacklistSqsProducer> response = getApprovedResponse(null, BL, context.getRiskResponseJpaEntity());
    return response;
  }
}
