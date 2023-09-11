package asia.atmonline.myriskservice.rules.cooldown.active_app;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.ACTIVE_APP;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.enums.application.CreditApplicationStatus;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CooldownActiveAppRule extends BaseCooldownRule<CooldownActiveAppContext> {

  public CooldownActiveAppRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseRiskJpaEntity execute(CooldownActiveAppContext context) {
    RiskResponseRiskJpaEntity response = super.execute(context);
    context.getCreditApplicationStatuses().forEach(status -> {
      if(status != null && status.isAlive()) {
        response.setDecision(REJECT);
        response.setRejectionReason(ACTIVE_APP);
      }
    });
    return response;
  }

  @Override
  public CooldownActiveAppContext getContext(List<CreditApplicationStatus> creditApplicationStatuses, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications) {
    return new CooldownActiveAppContext(creditApplicationStatuses);
  }
}