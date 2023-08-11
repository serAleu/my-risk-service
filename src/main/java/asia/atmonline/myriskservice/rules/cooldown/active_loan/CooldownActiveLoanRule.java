package asia.atmonline.myriskservice.rules.cooldown.active_loan;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.COOLDOWN;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.ACTIVE_LOAN;

import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownRule;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CooldownActiveLoanRule extends BaseCooldownRule<CooldownActiveLoanContext> {

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<CooldownSqsProducer> execute(CooldownActiveLoanContext context) {
    RiskResponseJpaEntity<CooldownSqsProducer> response = getApprovedResponse(context.getRiskResponseJpaEntity().getApplicationId(), COOLDOWN,
        context.getRiskResponseJpaEntity());
    context.getCreditList().forEach(credit -> {
      if(credit.getStatus() != null && !credit.getStatus().isEnding()) {
        response.setDecision(REJECT);
        response.setRejectionReasonCode(ACTIVE_LOAN);
      }
    });
    return response;
  }

  @Override
  public CooldownActiveLoanContext getCurrentCooldownRuleContext(List<CreditApplication> creditApplicationList, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications) {
    return new CooldownActiveLoanContext(creditList);
  }
}
