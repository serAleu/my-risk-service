package asia.atmonline.myriskservice.rules.cooldown.applim_2d;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.APPLIM_2D;

import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CooldownApplim2dRule extends BaseCooldownRule<CooldownApplim2dContext> {

  public CooldownApplim2dRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<CooldownSqsProducer> execute(CooldownApplim2dContext context) {
    RiskResponseJpaEntity<CooldownSqsProducer> response = super.execute(context);
    if(context.getNumOf2dApplications() > CooldownApplim2dContext.APPLICATIONS_LIMIT_NUM) {
      response.setDecision(REJECT);
      response.setRejectionReasonCode(APPLIM_2D);
    }
    return response;
  }

  @Override
  public CooldownApplim2dContext getContext(List<CreditApplication> creditApplicationList, List<Credit> creditList, Integer numOf2DApplications,
      Integer numOf5wApplications, Integer numOf9mApplications) {
    return new CooldownApplim2dContext(numOf2DApplications);
  }
}
