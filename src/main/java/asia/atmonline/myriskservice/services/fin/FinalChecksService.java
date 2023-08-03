package asia.atmonline.myriskservice.services.fin;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.FINAL;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.requests.impl.FinalRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.responses.impl.FinalResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.FinalRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.fin.FinalSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class FinalChecksService extends BaseChecksService<FinalRequest, FinalRequestJpaEntity, FinalResponseJpaEntity> {

  public FinalChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponse<FinalSqsProducer> process(FinalRequest request) {
    return new RiskResponse<>();
  }

  @Override
  public boolean accept(FinalRequest request) {
    return request != null && FINAL.equals(request.getCheck());
  }

  @Override
  public FinalRequestJpaEntity getRequestEntity(FinalRequest request) {
    return new FinalRequestJpaEntity();
  }

  @Override
  public FinalResponseJpaEntity getResponseEntity(RiskResponse<? extends BaseSqsProducer> response) {
    return new FinalResponseJpaEntity();
  }
}