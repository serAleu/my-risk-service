package asia.atmonline.myriskservice.services.fin;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.FINAL;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.FinalRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.FinalRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.fin.FinalSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class FinalChecksService extends BaseChecksService<FinalRequest, FinalRequestJpaEntity> {

  public FinalChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponseJpaEntity<FinalSqsProducer> process(FinalRequest request) {
    return new RiskResponseJpaEntity<>();
  }

  @Override
  public boolean accept(FinalRequest request) {
    return request != null && FINAL.equals(request.getCheck());
  }

  @Override
  public FinalRequestJpaEntity getRequestEntity(FinalRequest request) {
    return new FinalRequestJpaEntity().setBorrowerId(request.getBorrowerId());
  }
}