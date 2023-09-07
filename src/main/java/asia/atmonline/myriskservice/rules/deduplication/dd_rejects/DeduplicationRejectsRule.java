package asia.atmonline.myriskservice.rules.deduplication.dd_rejects;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_REJECTS;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_REJECTS_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationRejectsRule extends BaseDeduplicationRule<DeduplicationRejectsContext> {

  public DeduplicationRejectsRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseRiskJpaEntity execute(DeduplicationRejectsContext context) {
    RiskResponseRiskJpaEntity response = super.execute(context);
    if(context.getRejectedApplicationsCount() >= 2 && context.getApprovedApplicationsCount() == 0) {
      if (context.isFinalChecks) {
        response.setRejectionReason(DD_REJECTS_F);
      } else {
        response.setRejectionReason(DD_REJECTS);
      }
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public DeduplicationRejectsContext getContext(boolean isFinalChecks, int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationRejectsContext(isFinalChecks, approvedApplicationsCount, rejectedApplicationsCount);
  }
}
