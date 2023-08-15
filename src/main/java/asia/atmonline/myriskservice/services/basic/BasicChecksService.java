package asia.atmonline.myriskservice.services.basic;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.BASIC;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.BasicRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.BasicRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BasicChecksService extends BaseChecksService<BasicRequest, BasicRequestJpaEntity> {

  public BasicChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponseJpaEntity<BasicSqsProducer> process(BasicRequest request) {
    return new RiskResponseJpaEntity<>();
  }

  @Override
  public boolean accept(BasicRequest request) {
    return request != null && BASIC.equals(request.getCheck());
  }

  @Override
  public BasicRequestJpaEntity getRequestEntity(BasicRequest request) {
    return new BasicRequestJpaEntity().setBorrowerId(request.getBorrowerId());
  }
}