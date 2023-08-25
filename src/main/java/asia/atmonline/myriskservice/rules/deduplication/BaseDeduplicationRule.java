package asia.atmonline.myriskservice.rules.deduplication;

import static asia.atmonline.myriskservice.enums.risk.CheckType.DEDUP;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;

public abstract class BaseDeduplicationRule<P extends BaseDeduplicationContext> extends BaseRule<P> {

  public BaseDeduplicationRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(P context) {
    return getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), DEDUP, context.getRiskResponseJpaEntity());
  }

  public abstract P getContext(boolean isFinalChecks, int approvedApplicationsCount, int rejectedApplicationsCount, int countInProgress,
      int notFinishedCreditsCount, int maxDpdCount, boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum);
}
