package asia.atmonline.myriskservice.services;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;

public interface BaseRiskChecksService {

  RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request);
}