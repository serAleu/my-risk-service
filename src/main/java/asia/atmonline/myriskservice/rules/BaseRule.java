package asia.atmonline.myriskservice.rules;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.APPROVE;

import asia.atmonline.myriskservice.enums.risk.GroupOfChecks;
import asia.atmonline.myriskservice.enums.risk.RejectionReasonCode;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseRule<T extends BaseRuleContext> {

  private final BlacklistChecksService blacklistChecksService;

  public abstract RiskResponseJpaEntity<? extends BaseSqsProducer> execute(T context);

  public void saveToBlacklists(Long borrowerId, RejectionReasonCode code) {
    blacklistChecksService.save(borrowerId, code.getRuleId());
  }

  @SuppressWarnings({"rawtypes"})
  protected RiskResponseJpaEntity getApprovedResponse(Long applicationId, GroupOfChecks check, RiskResponseJpaEntity riskResponseJpaEntity) {
    riskResponseJpaEntity.setApplicationId(applicationId);
    riskResponseJpaEntity.setDecision(APPROVE);
    riskResponseJpaEntity.setCheck(check);
    return riskResponseJpaEntity;
  }
}