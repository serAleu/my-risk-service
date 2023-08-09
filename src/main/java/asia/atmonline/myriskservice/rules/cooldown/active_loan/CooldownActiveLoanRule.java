package asia.atmonline.myriskservice.rules.cooldown.active_loan;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.COOLDOWN;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.ACTIVE_LOAN;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import org.springframework.stereotype.Component;

@Component
public class CooldownActiveLoanRule extends BaseRule<CooldownActiveLoanContext> {

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<CooldownSqsProducer> execute(CooldownActiveLoanContext context) {
    RiskResponseJpaEntity<CooldownSqsProducer> response = getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), COOLDOWN,
        context.getRiskResponseJpaEntity());
    response.setDecision(REJECT);
    response.setRejectionReasonCode(ACTIVE_LOAN);
    return response;
  }
}
