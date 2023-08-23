package asia.atmonline.myriskservice.rules.deduplication.dd_actloan;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_ACTLOAN;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationActLoanRule extends BaseDeduplicationRule<DeduplicationActLoanContext> {

  public DeduplicationActLoanRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<DeduplicationSqsProducer> execute(DeduplicationActLoanContext context) {
    RiskResponseJpaEntity<DeduplicationSqsProducer> response = super.execute(context);
    if(context.getNotFinishedCreditsCount() > 0) {
      response.setDecision(REJECT);
      response.setRejection_reason_code(DD_ACTLOAN);
    }
    return response;
  }

  @Override
  public DeduplicationActLoanContext getContext(int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationActLoanContext(notFinishedCreditsCount);
  }
}
