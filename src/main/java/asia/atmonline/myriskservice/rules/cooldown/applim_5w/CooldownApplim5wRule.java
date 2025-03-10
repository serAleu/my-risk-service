package asia.atmonline.myriskservice.rules.cooldown.applim_5w;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.APPLIM_5W;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.enums.application.CreditApplicationStatus;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CooldownApplim5wRule extends BaseCooldownRule<CooldownApplim5wContext> {

  public CooldownApplim5wRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(CooldownApplim5wContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if(context.getNumOf5wApplications() > CooldownApplim5wContext.APPLICATIONS_LIMIT_NUM) {
      response.setDecision(REJECT);
      response.setRejectionReason(APPLIM_5W);
    }
    return response;
  }

  @Override
  public CooldownApplim5wContext getContext(RiskResponseJpaEntity response, List<CreditApplicationStatus> creditApplicationStatuses, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications) {
    return new CooldownApplim5wContext(response, numOf5wApplications);
  }
}
