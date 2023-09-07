package asia.atmonline.myriskservice.rules.cooldown;

import static asia.atmonline.myriskservice.enums.risk.CheckType.DEDUP;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.rules.BaseRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;

public abstract class BaseCooldownRule<P extends BaseCooldownContext> extends BaseRule<P> {

  public BaseCooldownRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseRiskJpaEntity execute(P context) {
    return getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), DEDUP, context.getRiskResponseJpaEntity());
  }

  public abstract P getContext(List<CreditApplication> creditApplicationList, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications);
}
