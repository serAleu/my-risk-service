package asia.atmonline.myriskservice.rules.cooldown.applim_9m;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.APPLIM_9M;

import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CooldownApplim9mRule extends BaseCooldownRule<CooldownApplim9mContext> {

  public CooldownApplim9mRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<CooldownSqsProducer> execute(CooldownApplim9mContext context) {
    RiskResponseJpaEntity<CooldownSqsProducer> response = super.execute(context);
    if(context.getNumOf9mApplications() > CooldownApplim9mContext.APPLICATIONS_LIMIT_NUM) {
      response.setDecision(REJECT);
      response.setRejectionReasonCode(APPLIM_9M);
    }
    return response;
  }

  @Override
  public CooldownApplim9mContext getContext(List<CreditApplication> creditApplicationList, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications) {
    return new CooldownApplim9mContext(numOf9mApplications);
  }
}
