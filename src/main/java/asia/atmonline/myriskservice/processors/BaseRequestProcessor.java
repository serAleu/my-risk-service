package asia.atmonline.myriskservice.processors;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;

public abstract class BaseRequestProcessor {

  public abstract boolean isSuitable(RiskRequestJpaEntity request);

  public abstract RiskResponseJpaEntity process(RiskRequestJpaEntity request);

}
