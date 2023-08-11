package asia.atmonline.myriskservice.rules.cooldown.active_app;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.COOLDOWN;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.ACTIVE_APP;

import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownRule;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CooldownActiveAppRule extends BaseCooldownRule<CooldownActiveAppContext> {

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<CooldownSqsProducer> execute(CooldownActiveAppContext context) {
    RiskResponseJpaEntity<CooldownSqsProducer> response = getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), COOLDOWN,
        context.getRiskResponseJpaEntity());
    context.getCreditApplicationList().forEach(application -> {
      if(application.getStatus() != null && application.getStatus().isAlive()) {
        response.setDecision(REJECT);
        response.setRejectionReasonCode(ACTIVE_APP);
      }
    });
    return response;
  }

  @Override
  public CooldownActiveAppContext getCurrentCooldownRuleContext(List<CreditApplication> creditApplicationList, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications) {
    return new CooldownActiveAppContext(creditApplicationList);
  }
}