package asia.atmonline.myriskservice.rules.deduplication.bl_account;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.BL_ACCOUNT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.BL_ACCOUNT_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationBlAccountRule extends BaseDeduplicationRule<DeduplicationBlAccountContext> {

  public DeduplicationBlAccountRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(DeduplicationBlAccountContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if(context.isBankAccountMatchedWithBlAccount()) {
      if (context.isFinalChecks) {
        response.setRejectionReason(BL_ACCOUNT_F);
      } else {
        response.setRejectionReason(BL_ACCOUNT);
      }
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public DeduplicationBlAccountContext getContext(boolean isFinalChecks, int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationBlAccountContext(isFinalChecks, isBankAccountMatchedWithBlAccount);
  }
}
