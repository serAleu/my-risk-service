package asia.atmonline.myriskservice.rules.deduplication.dd_maxdpd;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.DD_MAXDPD;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationMaxDpdRule extends BaseDeduplicationRule<DeduplicationMaxDpdContext> {

  public DeduplicationMaxDpdRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<DeduplicationSqsProducer> execute(DeduplicationMaxDpdContext context) {
    RiskResponseJpaEntity<DeduplicationSqsProducer> response = super.execute(context);
    if(context.getMaxDpdCount() > 0) {
      response.setDecision(REJECT);
      response.setRejectionReasonCode(DD_MAXDPD);
    }
    return response;
  }

  @Override
  public DeduplicationMaxDpdContext getContext(int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationMaxDpdContext(maxDpdCount);
  }
}
