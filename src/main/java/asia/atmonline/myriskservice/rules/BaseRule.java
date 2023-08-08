package asia.atmonline.myriskservice.rules;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.APPROVE;

import asia.atmonline.myriskservice.enums.risk.GroupOfChecks;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;

public abstract class BaseRule<T extends BaseRuleContext> {

  public abstract RiskResponseJpaEntity<? extends BaseSqsProducer> execute(T context);

  @SuppressWarnings({"rawtypes"})
  protected RiskResponseJpaEntity getApprovedResponse(Long applicationId, GroupOfChecks check, RiskResponseJpaEntity riskResponseJpaEntity) {
    riskResponseJpaEntity.setApplicationId(applicationId);
    riskResponseJpaEntity.setDecision(APPROVE);
    riskResponseJpaEntity.setCheck(check);
    return riskResponseJpaEntity;
  }
}