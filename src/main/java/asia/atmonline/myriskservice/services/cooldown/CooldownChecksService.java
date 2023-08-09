package asia.atmonline.myriskservice.services.cooldown;

import static asia.atmonline.myriskservice.enums.risk.GroupOfChecks.COOLDOWN;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.CooldownRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.CooldownRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class CooldownChecksService extends BaseChecksService<CooldownRequest, CooldownRequestJpaEntity> {

  public CooldownChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories) {
    super(repositories);
  }

  @Override
  public RiskResponseJpaEntity<CooldownSqsProducer> process(CooldownRequest request) {
    return new RiskResponseJpaEntity<>();
  }

  @Override
  public boolean accept(CooldownRequest request) {
    return request != null && COOLDOWN.equals(request.getCheck());
  }

  @Override
  public CooldownRequestJpaEntity getRequestEntity(CooldownRequest request) {
    return new CooldownRequestJpaEntity();
  }
}