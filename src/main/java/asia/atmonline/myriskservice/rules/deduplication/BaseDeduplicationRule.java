package asia.atmonline.myriskservice.rules.deduplication;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.DEDUP;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;

public abstract class BaseDeduplicationRule<P extends BaseDeduplicationContext> extends BaseRule<P> {

  public BaseDeduplicationRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<DeduplicationSqsProducer> execute(P context) {
    return (RiskResponseJpaEntity<DeduplicationSqsProducer>) getApprovedResponse(context.getRiskResponseJpaEntity().getCreditApplicationId(), DEDUP,
        context.getRiskResponseJpaEntity());
  }

  public abstract P getContext(boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum);
}
