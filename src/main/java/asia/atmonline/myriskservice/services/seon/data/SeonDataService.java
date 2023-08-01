package asia.atmonline.myriskservice.services.seon.data;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.SEON_BLACKBOX;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.requests.impl.SeonDataRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.impl.SeonDataResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.SeonDataRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.seon_data.SeonDataSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SeonDataService extends BaseChecksService<SeonDataRequest, SeonDataRequestJpaEntity, SeonDataResponseJpaEntity> {

  public SeonDataService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponse<SeonDataSqsProducer> process(SeonDataRequest request) {
    return new RiskResponse<>();
  }

  @Override
  public boolean accept(SeonDataRequest request) {
    return request != null && SEON_BLACKBOX.equals(request.getCheck());
  }

  @Override
  public SeonDataRequestJpaEntity getRequestEntity(SeonDataRequest request) {
    return new SeonDataRequestJpaEntity();
  }

  @Override
  public SeonDataResponseJpaEntity getResponseEntity(RiskResponse<? extends BaseSqsProducer> response) {
    return new SeonDataResponseJpaEntity();
  }
}