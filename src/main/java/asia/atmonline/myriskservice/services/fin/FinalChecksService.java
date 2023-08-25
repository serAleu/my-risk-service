package asia.atmonline.myriskservice.services.fin;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import asia.atmonline.myriskservice.services.basic.BasicChecksService;
import asia.atmonline.myriskservice.services.dedup.DeduplicationChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FinalChecksService implements BaseRiskChecksService {

  private final BasicChecksService basicChecksService;
  private final DeduplicationChecksService deduplicationChecksService;

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = basicChecksService.process(request, true);
    RiskResponseJpaEntity response1 = deduplicationChecksService.process(request, true);
    return response;
  }
}