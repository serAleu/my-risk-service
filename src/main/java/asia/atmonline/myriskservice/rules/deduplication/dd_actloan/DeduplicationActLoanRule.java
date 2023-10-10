package asia.atmonline.myriskservice.rules.deduplication.dd_actloan;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_ACTLOAN;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_ACTLOAN_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationActLoanRule extends BaseDeduplicationRule<DeduplicationActLoanContext> {

  public DeduplicationActLoanRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(DeduplicationActLoanContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if(context.getNotFinishedCreditsCount() > 0) {
      if (context.isFinalChecks) {
        response.setRejectionReason(DD_ACTLOAN_F);
      } else {
        response.setRejectionReason(DD_ACTLOAN);
      }
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public DeduplicationActLoanContext getContext(RiskResponseJpaEntity response, boolean isFinalChecks, int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationActLoanContext(response, isFinalChecks, notFinishedCreditsCount);
  }
}
