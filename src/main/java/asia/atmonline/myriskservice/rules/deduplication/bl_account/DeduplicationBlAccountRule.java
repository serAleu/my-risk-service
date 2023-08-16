package asia.atmonline.myriskservice.rules.deduplication.bl_account;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.BL_ACCOUNT;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationBlAccountRule extends BaseDeduplicationRule<DeduplicationBlAccountContext> {

  public DeduplicationBlAccountRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<DeduplicationSqsProducer> execute(DeduplicationBlAccountContext context) {
    RiskResponseJpaEntity<DeduplicationSqsProducer> response = super.execute(context);
    if(context.isBankAccountMatchedWithBlAccount()) {
      response.setDecision(REJECT);
      response.setRejectionReasonCode(BL_ACCOUNT);
    }
    return response;
  }

  @Override
  public DeduplicationBlAccountContext getContext(int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationBlAccountContext(isBankAccountMatchedWithBlAccount);
  }
}
