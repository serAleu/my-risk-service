package asia.atmonline.myriskservice.processors;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.APPROVE;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;

public abstract class BaseRequestProcessor {

  public abstract boolean isSuitable(RiskRequestJpaEntity request);

  public abstract RiskResponseJpaEntity process(RiskRequestJpaEntity request);

  public RiskResponseJpaEntity getMockApprovedResponse(RiskRequestJpaEntity request, String message) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    response.setApplicationId(request.getApplicationId());
    response.setCheckType(request.getCheckType());
    response.setDecision(APPROVE);
    response.setMessage(message);
    response.setRequestId(request.getId());
    return response;
  }

}
