package asia.atmonline.myriskservice.rules.cooldown.active_loan;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.ACTIVE_LOAN;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.enums.application.CreditApplicationStatus;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CooldownActiveLoanRule extends BaseCooldownRule<CooldownActiveLoanContext> {

  public CooldownActiveLoanRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseRiskJpaEntity execute(CooldownActiveLoanContext context) {
    RiskResponseRiskJpaEntity response = super.execute(context);
    context.getCreditList().forEach(credit -> {
      if(credit.getStatus() != null && !credit.getStatus().isEnding()) {
        response.setDecision(REJECT);
        response.setRejectionReason(ACTIVE_LOAN);
      }
    });
    return response;
  }

  @Override
  public CooldownActiveLoanContext getContext(List<CreditApplicationStatus> creditApplicationStatuses, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications) {
    return new CooldownActiveLoanContext(creditList);
  }
}
