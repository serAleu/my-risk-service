package asia.atmonline.myriskservice.rules.cooldown;

import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import java.util.List;

public abstract class BaseCooldownRule<P extends BaseCooldownContext> extends BaseRule<P> {

  @Override
  public abstract RiskResponseJpaEntity<CooldownSqsProducer> execute(P context);

  public abstract P getCurrentCooldownRuleContext(List<CreditApplication> creditApplicationList, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications);

}
