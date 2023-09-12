package asia.atmonline.myriskservice.services;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;

public interface BaseRiskChecksService {

  RiskResponseJpaEntity process(RiskRequestJpaEntity request);
}