package asia.atmonline.myriskservice.rules.blacklist.phone;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BL;
import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.BLACKLIST;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRule;
import org.springframework.stereotype.Component;

@Component
public class BlacklistPhoneRule extends BaseRule<BlacklistPhoneContext> {

//  public BlacklistPhoneRule(BlacklistChecksService blacklistChecksService) {
//    super(blacklistChecksService);
//  }

  @Override
  public RiskResponseJpaEntity execute(BlacklistPhoneContext context) {
    RiskResponseJpaEntity response = getApprovedResponse(null, BL, context.getRiskResponseJpaEntity());
    Integer numberOfNotFinishedCredits = context.getNumberOfNotFinishedCredits();
//    response.setAdditionalField("bl_level", String.valueOf(numberOfNotFinishedCredits));
    context.getEntities().forEach(entity -> {
      if(entity != null && numberOfNotFinishedCredits <= 0 && context.getNumberOfFinishedCredits() <= entity.getBlLevel()) {
        response.setDecision(REJECT);
        response.setRejectionReason(BLACKLIST);
      }
    });
    return response;
  }
}
