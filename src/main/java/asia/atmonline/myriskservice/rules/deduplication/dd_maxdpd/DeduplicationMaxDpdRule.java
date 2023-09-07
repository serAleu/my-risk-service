package asia.atmonline.myriskservice.rules.deduplication.dd_maxdpd;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_MAXDPD;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_MAXDPD_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationMaxDpdRule extends BaseDeduplicationRule<DeduplicationMaxDpdContext> {

  public DeduplicationMaxDpdRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseRiskJpaEntity execute(DeduplicationMaxDpdContext context) {
    RiskResponseRiskJpaEntity response = super.execute(context);
    if(context.getMaxDpdCount() > 0) {
      if (context.isFinalChecks) {
        response.setRejectionReason(DD_MAXDPD_F);
      } else {
        response.setRejectionReason(DD_MAXDPD);
      }
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public DeduplicationMaxDpdContext getContext(boolean isFinalChecks, int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationMaxDpdContext(isFinalChecks, maxDpdCount);
  }
}
