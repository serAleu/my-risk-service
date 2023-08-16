package asia.atmonline.myriskservice.rules.deduplication.dd_rejects;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_REJECTS;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationRejectsRule extends BaseDeduplicationRule<DeduplicationRejectsContext> {

  public DeduplicationRejectsRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<DeduplicationSqsProducer> execute(DeduplicationRejectsContext context) {
    RiskResponseJpaEntity<DeduplicationSqsProducer> response = super.execute(context);
    if(context.getRejectedApplicationsCount() >= 2 && context.getApprovedApplicationsCount() == 0) {
      response.setDecision(REJECT);
      response.setRejectionReasonCode(DD_REJECTS);
    }
    return response;
  }

  @Override
  public DeduplicationRejectsContext getContext(int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationRejectsContext(approvedApplicationsCount, rejectedApplicationsCount);
  }
}
