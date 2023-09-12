package asia.atmonline.myriskservice.rules;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.APPROVE;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.enums.risk.CheckType;
import asia.atmonline.myriskservice.enums.risk.RejectionReasonCode;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;

public abstract class BaseRule<T extends BaseRuleContext> {

  private BlacklistChecksService blacklistChecksService;

  public BaseRule(BlacklistChecksService blacklistChecksService) {
    this.blacklistChecksService = blacklistChecksService;
  }

  public BaseRule() {
  }

  public abstract RiskResponseJpaEntity execute(T context);

  public void saveToBlacklists(Long applicationId, Long borrowerId, RejectionReasonCode code) {
    blacklistChecksService.save(applicationId, borrowerId, code);
  }

  protected RiskResponseJpaEntity getApprovedResponse(Long creditApplicationId, CheckType check, RiskResponseJpaEntity riskResponseJpaEntity) {
    riskResponseJpaEntity.setApplicationId(creditApplicationId);
    riskResponseJpaEntity.setDecision(APPROVE);
    riskResponseJpaEntity.setCheckType(check);
    return riskResponseJpaEntity;
  }
}