package asia.atmonline.myriskservice.rules.blacklist.phone;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BL;
import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.BL_PHONE;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRule;
import org.springframework.stereotype.Component;

@Component
public class BlacklistPhoneRule extends BaseRule<BlacklistPhoneContext> {

  @Override
  public RiskResponseJpaEntity execute(BlacklistPhoneContext context) {
    RiskResponseJpaEntity response = getApprovedResponse(null, BL, context.getRiskResponseJpaEntity());
    Integer numberOfNotFinishedCredits = context.getNumberOfNotFinishedCredits();
    context.getEntities().forEach(entity -> {
      if(entity != null && numberOfNotFinishedCredits <= 0 && context.getNumberOfFinishedCredits() <= entity.getBlLevel()) {
        response.setDecision(REJECT);
        response.setRejectionReason(BL_PHONE);
      }
    });
    return response;
  }
}
