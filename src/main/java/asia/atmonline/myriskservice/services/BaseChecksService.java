package asia.atmonline.myriskservice.services;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.BaseRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public abstract class BaseChecksService<R extends BaseRequest, E extends BaseJpaEntity, S extends BaseJpaEntity> {

  private final Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories;

  public abstract RiskResponseJpaEntity<? extends BaseSqsProducer> process(R request);
  public abstract boolean accept(R request);
  public abstract E getRequestEntity(R request);

//  public abstract S getResponseEntity(RiskResponseJpaEntity<? extends BaseSqsProducer> response);

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void save(BaseJpaEntity entity) {
    BaseJpaRepository repository = repositories.get(entity.repositoryName());
    repository.save(entity);
  }
}