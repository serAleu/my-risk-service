package asia.atmonline.myriskservice.services.fin;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
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
  public RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request) {
    RiskResponseRiskJpaEntity response = basicChecksService.process(request, true);
    if(REJECT.equals(response.getDecision())) {
      return response;
    }
    response = deduplicationChecksService.process(request, true);
    return response;
  }
}