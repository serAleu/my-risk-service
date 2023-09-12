package asia.atmonline.myriskservice.processors;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;

public abstract class BaseRequestProcessor {

  public abstract boolean isSuitable(RiskRequestJpaEntity request);

  public abstract RiskResponseJpaEntity process(RiskRequestJpaEntity request);

}
