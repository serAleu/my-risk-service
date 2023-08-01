package asia.atmonline.myriskservice.services.seon.blackbox;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.SEON_BLACKBOX;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.requests.impl.SeonBlackboxRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.impl.SeonBlackboxResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.SeonBlackboxRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.seon_blackbox.SeonBlackboxSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SeonBlackboxService extends BaseChecksService<SeonBlackboxRequest, SeonBlackboxRequestJpaEntity, SeonBlackboxResponseJpaEntity> {

  public SeonBlackboxService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponse<SeonBlackboxSqsProducer> process(SeonBlackboxRequest request) {
    return new RiskResponse<>();
  }

  @Override
  public boolean accept(SeonBlackboxRequest request) {
    return request != null && SEON_BLACKBOX.equals(request.getCheck());
  }

  @Override
  public SeonBlackboxRequestJpaEntity getRequestEntity(SeonBlackboxRequest request) {
    return new SeonBlackboxRequestJpaEntity();
  }

  @Override
  public SeonBlackboxResponseJpaEntity getResponseEntity(RiskResponse<? extends BaseSqsProducer> response) {
    return new SeonBlackboxResponseJpaEntity();
  }
}