package asia.atmonline.myriskservice.rules.deduplication.bl_passport;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.BL_PASSPORT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.BL_PASSPORT_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationBlPassportRule extends BaseDeduplicationRule<DeduplicationBlPassportContext> {

  public DeduplicationBlPassportRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(DeduplicationBlPassportContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if(context.isPassportNumMatchedWithBlPassportNum()) {
      if (context.isFinalChecks) {
        response.setRejectionReason(BL_PASSPORT_F);
      } else {
        response.setRejectionReason(BL_PASSPORT);
      }
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public DeduplicationBlPassportContext getContext(boolean isFinalChecks, int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationBlPassportContext(isFinalChecks, isPassportNumMatchedWithBlPassportNum);
  }
}
