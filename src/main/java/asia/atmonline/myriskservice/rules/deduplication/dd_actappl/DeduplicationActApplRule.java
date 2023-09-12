package asia.atmonline.myriskservice.rules.deduplication.dd_actappl;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_ACTAPPL;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_ACTAPPL_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationActApplRule extends BaseDeduplicationRule<DeduplicationActApplContext> {

  public DeduplicationActApplRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(DeduplicationActApplContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if(context.getCountInProgress() > 0) {
      if (context.isFinalChecks) {
        response.setRejectionReason(DD_ACTAPPL_F);
      } else {
        response.setRejectionReason(DD_ACTAPPL);
      }
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public DeduplicationActApplContext getContext(boolean isFinalChecks, int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationActApplContext(isFinalChecks, countInProgress);
  }
}