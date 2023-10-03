package asia.atmonline.myriskservice.services.fin;

import static asia.atmonline.myriskservice.enums.risk.CheckType.FINAL;
import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
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
    if(!REJECT.equals(response.getDecision())) {
      response = deduplicationChecksService.process(request, true);
    }
    response.setCheckType(FINAL);
    return response;
  }
}