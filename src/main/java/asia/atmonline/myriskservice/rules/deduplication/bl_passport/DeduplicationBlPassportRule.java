package asia.atmonline.myriskservice.rules.deduplication.bl_passport;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.BL_PASSPORT;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationBlPassportRule extends BaseDeduplicationRule<DeduplicationBlPassportContext> {

  public DeduplicationBlPassportRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<DeduplicationSqsProducer> execute(DeduplicationBlPassportContext context) {
    RiskResponseJpaEntity<DeduplicationSqsProducer> response = super.execute(context);
    if(context.isPassportNumMatchedWithBlPassportNum()) {
      response.setDecision(REJECT);
      response.setRejectionReasonCode(BL_PASSPORT);
    }
    return response;
  }

  @Override
  public DeduplicationBlPassportContext getContext(int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationBlPassportContext(isPassportNumMatchedWithBlPassportNum);
  }
}
