package asia.atmonline.myriskservice.rules.cooldown.applim_9m;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.COOLDOWN;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.APPLIM_9M;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import org.springframework.stereotype.Component;

@Component
public class CooldownApplim9mRule extends BaseRule<CooldownApplim9mContext> {

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<CooldownSqsProducer> execute(CooldownApplim9mContext context) {
    RiskResponseJpaEntity<CooldownSqsProducer> response = getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), COOLDOWN,
        context.getRiskResponseJpaEntity());
    response.setDecision(REJECT);
    response.setRejectionReasonCode(APPLIM_9M);
    return response;
  }
}
