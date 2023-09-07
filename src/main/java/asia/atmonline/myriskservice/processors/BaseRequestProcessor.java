package asia.atmonline.myriskservice.processors;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;

public abstract class BaseRequestProcessor {

  public abstract boolean isSuitable(RiskRequestRiskJpaEntity request);

  public abstract RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request);

}
