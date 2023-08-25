package asia.atmonline.myriskservice.services;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;

public interface BaseRiskChecksService {

  RiskResponseJpaEntity process(RiskRequestJpaEntity request);
}