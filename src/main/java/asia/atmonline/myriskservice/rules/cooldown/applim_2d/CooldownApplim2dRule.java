package asia.atmonline.myriskservice.rules.cooldown.applim_2d;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.COOLDOWN;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.APPLIM_2D;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import org.springframework.stereotype.Component;

@Component
public class CooldownApplim2dRule extends BaseRule<CooldownApplim2dContext> {

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<CooldownSqsProducer> execute(CooldownApplim2dContext context) {
    RiskResponseJpaEntity<CooldownSqsProducer> response = getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), COOLDOWN,
        context.getRiskResponseJpaEntity());
    response.setDecision(REJECT);
    response.setRejectionReasonCode(APPLIM_2D);
    return response;
  }
}
