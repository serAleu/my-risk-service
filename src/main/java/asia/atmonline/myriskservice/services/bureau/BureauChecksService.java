package asia.atmonline.myriskservice.services.bureau;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BureauChecksService implements BaseRiskChecksService {

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    return new RiskResponseJpaEntity();
  }
}