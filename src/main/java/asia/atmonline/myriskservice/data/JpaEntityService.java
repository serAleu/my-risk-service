package asia.atmonline.myriskservice.data;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.risk.RiskRequestJpaRepository;
import asia.atmonline.myriskservice.data.repositories.impl.risk.RiskResponseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JpaEntityService {

  private final RiskResponseJpaRepository responseJpaRepository;
  private final RiskRequestJpaRepository requestJpaRepository;

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public RiskRequestJpaEntity save(RiskRequestJpaEntity request) {
    return requestJpaRepository.save(request);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public RiskResponseJpaEntity save(RiskResponseJpaEntity response) {
    return responseJpaRepository.save(response);
  }

}
