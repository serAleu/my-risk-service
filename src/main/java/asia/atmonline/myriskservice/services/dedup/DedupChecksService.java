package asia.atmonline.myriskservice.services.dedup;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.DEDUP;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.DedupRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.DedupRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.dedup.DedupSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DedupChecksService extends BaseChecksService<DedupRequest, DedupRequestJpaEntity> {

  public DedupChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponseJpaEntity<DedupSqsProducer> process(DedupRequest request) {
    return new RiskResponseJpaEntity<>();
  }

  @Override
  public boolean accept(DedupRequest request) {
    return request != null && DEDUP.equals(request.getCheck());
  }

  @Override
  public DedupRequestJpaEntity getRequestEntity(DedupRequest request) {
    return new DedupRequestJpaEntity();
  }
}