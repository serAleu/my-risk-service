package asia.atmonline.myriskservice.services.dedup;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.DEDUP3;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.requests.impl.Dedup3RequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.responses.impl.Dedup3ResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.Dedup3Request;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.dedup3.Dedup3SqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class Dedup3ChecksService extends BaseChecksService<Dedup3Request, Dedup3RequestJpaEntity, Dedup3ResponseJpaEntity> {

  public Dedup3ChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponse<Dedup3SqsProducer> process(Dedup3Request request) {
    return new RiskResponse<>();
  }

  @Override
  public boolean accept(Dedup3Request request) {
    return request != null && DEDUP3.equals(request.getCheck());
  }

  @Override
  public Dedup3RequestJpaEntity getRequestEntity(Dedup3Request request) {
    return new Dedup3RequestJpaEntity();
  }

  @Override
  public Dedup3ResponseJpaEntity getResponseEntity(RiskResponse<? extends BaseSqsProducer> response) {
    return new Dedup3ResponseJpaEntity();
  }
}