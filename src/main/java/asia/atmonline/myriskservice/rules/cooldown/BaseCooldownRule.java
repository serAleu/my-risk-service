package asia.atmonline.myriskservice.rules.cooldown;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.DEDUP;

import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;

public abstract class BaseCooldownRule<P extends BaseCooldownContext> extends BaseRule<P> {

  public BaseCooldownRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<CooldownSqsProducer> execute(P context) {
    return (RiskResponseJpaEntity<CooldownSqsProducer>) getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), DEDUP,
        context.getRiskResponseJpaEntity());
  }

  public abstract P getContext(List<CreditApplication> creditApplicationList, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications);
}
