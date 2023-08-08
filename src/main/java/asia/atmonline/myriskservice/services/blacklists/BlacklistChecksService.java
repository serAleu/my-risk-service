package asia.atmonline.myriskservice.services.blacklists;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.BL;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.BlacklistRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.BlacklistResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.BlacklistsRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.blacklist.BlacklistSqsProducer;
import asia.atmonline.myriskservice.rules.blacklist.BlacklistPhoneContext;
import asia.atmonline.myriskservice.rules.blacklist.BlacklistPhoneRule;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlacklistChecksService extends BaseChecksService<BlacklistsRequest, BlacklistRequestJpaEntity, BlacklistResponseJpaEntity> {

  private final BlacklistPhoneRule blacklistPhoneRule;

  public BlacklistChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories, BlacklistPhoneRule blacklistPhoneRule) {
    super(repositories);
    this.blacklistPhoneRule = blacklistPhoneRule;
  }

  @Override
  public RiskResponseJpaEntity<BlacklistSqsProducer> process(BlacklistsRequest request) {
    return blacklistPhoneRule.execute(new BlacklistPhoneContext(request.getPhone_num()));
  }

  @Override
  public boolean accept(BlacklistsRequest request) {
    return request != null && BL.equals(request.getCheck()) && request.getPhone_num() != null;
  }

  @Override
  public BlacklistRequestJpaEntity getRequestEntity(BlacklistsRequest request) {
    return new BlacklistRequestJpaEntity().setPhoneNum(request.getPhone_num());
  }

//  @Override
//  public BlacklistResponseJpaEntity getResponseEntity(RiskResponseJpaEntity<? extends BaseSqsProducer> response) {
//    return new BlacklistResponseJpaEntity();
//  }
}