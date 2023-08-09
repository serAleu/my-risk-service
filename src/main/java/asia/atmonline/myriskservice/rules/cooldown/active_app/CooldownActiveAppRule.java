package asia.atmonline.myriskservice.rules.cooldown.active_app;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.COOLDOWN;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.ACTIVE_APP;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import asia.atmonline.myriskservice.rules.cooldown.applim_9m.CooldownApplim9mContext;
import org.springframework.stereotype.Component;

@Component
public class CooldownActiveAppRule extends BaseRule<CooldownApplim9mContext> {

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<CooldownSqsProducer> execute(CooldownApplim9mContext context) {
    RiskResponseJpaEntity<CooldownSqsProducer> response = getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), COOLDOWN,
        context.getRiskResponseJpaEntity());
    response.setDecision(REJECT);
    response.setRejectionReasonCode(ACTIVE_APP);
    return response;
  }
}