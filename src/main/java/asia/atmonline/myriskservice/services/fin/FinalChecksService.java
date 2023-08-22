package asia.atmonline.myriskservice.services.fin;

import static asia.atmonline.myriskservice.enums.risk.CheckType.FINAL;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.producers.fin.FinalSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class FinalChecksService extends BaseChecksService {

  public FinalChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponseJpaEntity<FinalSqsProducer> process(RiskRequestJpaEntity request) {
    return new RiskResponseJpaEntity<>();
  }

  @Override
  public boolean accept(RiskRequestJpaEntity request) {
    return request != null && FINAL.equals(request.getCheckType()) && request.getCreditApplicationId() != null;
  }
}