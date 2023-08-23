package asia.atmonline.myriskservice.services;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public abstract class BaseChecksService {

  private final Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories;
  public abstract RiskResponseJpaEntity<? extends BaseSqsProducer> process(RiskRequestJpaEntity request);
  public abstract boolean accept(RiskRequestJpaEntity request);
  public abstract <P extends BaseSqsProducer> P getProducer();

  @SuppressWarnings({"unchecked", "rawtypes"})
  public Long save(BaseJpaEntity entity) {
    BaseJpaRepository repository = repositories.get(entity.repositoryName());
    entity = (BaseJpaEntity) repository.save(entity);
    return entity.getId();
  }
}