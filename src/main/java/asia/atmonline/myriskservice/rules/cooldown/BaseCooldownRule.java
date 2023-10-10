package asia.atmonline.myriskservice.rules.cooldown;

import static asia.atmonline.myriskservice.enums.risk.CheckType.COOLDOWN;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.enums.application.CreditApplicationStatus;
import asia.atmonline.myriskservice.rules.BaseRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;

public abstract class BaseCooldownRule<P extends BaseCooldownContext> extends BaseRule<P> {

  public BaseCooldownRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(P context) {
    return getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), COOLDOWN, context.getRiskResponseJpaEntity());
  }

  public abstract P getContext(RiskResponseJpaEntity response, List<CreditApplicationStatus> creditApplicationStatuses, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications);
}
