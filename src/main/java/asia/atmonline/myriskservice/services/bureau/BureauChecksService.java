package asia.atmonline.myriskservice.services.bureau;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.BASIC;
import static asia.atmonline.myriskservice.enums.GroupOfChecks.BUREAU;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.requests.impl.BureauRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.impl.BureauResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.BureauRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.bureau.BureauSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BureauChecksService extends BaseChecksService<BureauRequest, BureauRequestJpaEntity, BureauResponseJpaEntity> {

  public BureauChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponse<BureauSqsProducer> process(BureauRequest request) {
    return new RiskResponse<>();
  }

  @Override
  public boolean accept(BureauRequest request) {
    return request != null && BUREAU.equals(request.getCheck());
  }

  @Override
  public BureauRequestJpaEntity getRequestEntity(BureauRequest request) {
    return new BureauRequestJpaEntity();
  }

  @Override
  public BureauResponseJpaEntity getResponseEntity(RiskResponse<? extends BaseSqsProducer> response) {
    return new BureauResponseJpaEntity();
  }
}