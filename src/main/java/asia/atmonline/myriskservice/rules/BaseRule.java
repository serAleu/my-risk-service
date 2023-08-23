package asia.atmonline.myriskservice.rules;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.APPROVE;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.enums.risk.CheckType;
import asia.atmonline.myriskservice.enums.risk.RejectionReasonCode;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;

public abstract class BaseRule<T extends BaseRuleContext> {

  private BlacklistChecksService blacklistChecksService;

  public BaseRule(BlacklistChecksService blacklistChecksService) {
    this.blacklistChecksService = blacklistChecksService;
  }

  public BaseRule() {
  }

  public abstract RiskResponseJpaEntity<? extends BaseSqsProducer> execute(T context);

  public void saveToBlacklists(Long borrowerId, RejectionReasonCode code) {
    blacklistChecksService.save(borrowerId, code.getRuleId());
  }

  @SuppressWarnings({"rawtypes"})
  protected RiskResponseJpaEntity getApprovedResponse(Long creditApplicationId, CheckType check, RiskResponseJpaEntity riskResponseJpaEntity) {
    riskResponseJpaEntity.setCredit_application_id(creditApplicationId);
    riskResponseJpaEntity.setDecision(APPROVE);
    riskResponseJpaEntity.setCheck(check);
    return riskResponseJpaEntity;
  }
}