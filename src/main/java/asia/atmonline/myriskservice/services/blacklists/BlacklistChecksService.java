package asia.atmonline.myriskservice.services.blacklists;

import static asia.atmonline.myriskservice.enums.GroupOfChecks.BL;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.requests.impl.BlacklistRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.impl.BlacklistResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.BlacklistsRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.blacklist.BlacklistSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class BlacklistChecksService extends BaseChecksService<BlacklistsRequest, BlacklistRequestJpaEntity, BlacklistResponseJpaEntity> {

  public BlacklistChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponse<BlacklistSqsProducer> process(BlacklistsRequest request) {
    return new RiskResponse<>();
  }

  @Override
  public boolean accept(BlacklistsRequest request) {
    return request != null && BL.equals(request.getCheck());
  }

  @Override
  public BlacklistRequestJpaEntity getRequestEntity(BlacklistsRequest request) {
    return new BlacklistRequestJpaEntity();
  }

  @Override
  public BlacklistResponseJpaEntity getResponseEntity(RiskResponse<? extends BaseSqsProducer> response) {
    return new BlacklistResponseJpaEntity();
  }
}