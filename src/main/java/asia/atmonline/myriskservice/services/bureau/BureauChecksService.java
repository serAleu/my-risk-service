package asia.atmonline.myriskservice.services.bureau;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.BUREAU;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.BureauRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.BureauRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.bureau.BureauSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BureauChecksService extends BaseChecksService<BureauRequest, BureauRequestJpaEntity> {

  public BureauChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponseJpaEntity<BureauSqsProducer> process(BureauRequest request) {
    return new RiskResponseJpaEntity<>();
  }

  @Override
  public boolean accept(BureauRequest request) {
    return request != null && BUREAU.equals(request.getCheck());
  }

  @Override
  public BureauRequestJpaEntity getRequestEntity(BureauRequest request) {
    return new BureauRequestJpaEntity();
  }
}