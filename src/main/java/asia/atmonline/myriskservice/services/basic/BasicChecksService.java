package asia.atmonline.myriskservice.services.basic;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.BASIC;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.requests.impl.BasicRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.impl.BasicResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.BasicRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BasicChecksService extends BaseChecksService<BasicRequest, BasicRequestJpaEntity, BasicResponseJpaEntity> {

  public BasicChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponse<BasicSqsProducer> process(BasicRequest request) {
    return new RiskResponse<>();
  }

  @Override
  public boolean accept(BasicRequest request) {
    return request != null && BASIC.equals(request.getCheck());
  }

  @Override
  public BasicRequestJpaEntity getRequestEntity(BasicRequest request) {
    return new BasicRequestJpaEntity();
  }

  @Override
  public BasicResponseJpaEntity getResponseEntity(RiskResponse<? extends BaseSqsProducer> response) {
    return new BasicResponseJpaEntity();
  }
}