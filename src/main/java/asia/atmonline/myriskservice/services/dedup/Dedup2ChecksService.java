package asia.atmonline.myriskservice.services.dedup;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.DEDUP2;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.Dedup2RequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.Dedup2ResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.Dedup2Request;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.dedup2.Dedup2SqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class Dedup2ChecksService extends BaseChecksService<Dedup2Request, Dedup2RequestJpaEntity, Dedup2ResponseJpaEntity> {

  public Dedup2ChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponseJpaEntity<Dedup2SqsProducer> process(Dedup2Request request) {
    return new RiskResponseJpaEntity<>();
  }

  @Override
  public boolean accept(Dedup2Request request) {
    return request != null && DEDUP2.equals(request.getCheck());
  }

  @Override
  public Dedup2RequestJpaEntity getRequestEntity(Dedup2Request request) {
    return new Dedup2RequestJpaEntity();
  }

//  @Override
//  public Dedup2ResponseJpaEntity getResponseEntity(RiskResponseJpaEntity<? extends BaseSqsProducer> response) {
//    return new Dedup2ResponseJpaEntity();
//  }
}