package asia.atmonline.myriskservice.rules.cooldown.applim_5w;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.COOLDOWN;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.APPLIM_5W;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import org.springframework.stereotype.Component;

@Component
public class CooldownApplim5wRule extends BaseRule<CooldownApplim5wContext> {

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<CooldownSqsProducer> execute(CooldownApplim5wContext context) {
    RiskResponseJpaEntity<CooldownSqsProducer> response = getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), COOLDOWN,
        context.getRiskResponseJpaEntity());
    response.setDecision(REJECT);
    response.setRejectionReasonCode(APPLIM_5W);
    return response;
  }
}
