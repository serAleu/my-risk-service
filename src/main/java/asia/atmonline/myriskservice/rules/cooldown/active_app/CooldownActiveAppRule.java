package asia.atmonline.myriskservice.rules.cooldown.active_app;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.ACTIVE_APP;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
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
  public RiskResponseJpaEntity execute(CooldownActiveAppContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    context.getCreditApplicationList().forEach(application -> {
      if(application.getStatus() != null && application.getStatus().isAlive()) {
        response.setDecision(REJECT);
        response.setRejectionReason(ACTIVE_APP);
      }
    });
    return response;
  }

  @Override
  public CooldownActiveAppContext getContext(List<CreditApplication> creditApplicationList, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications) {
    return new CooldownActiveAppContext(creditApplicationList);
  }
}