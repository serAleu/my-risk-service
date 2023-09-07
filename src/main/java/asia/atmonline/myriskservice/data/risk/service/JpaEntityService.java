package asia.atmonline.myriskservice.data.risk.service;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.RiskRequestJpaRepository;
import asia.atmonline.myriskservice.data.risk.repositories.RiskResponseJpaRepository;
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
  public RiskRequestRiskJpaEntity save(RiskRequestRiskJpaEntity request) {
    return requestJpaRepository.save(request);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public RiskResponseRiskJpaEntity save(RiskResponseRiskJpaEntity response) {
    return responseJpaRepository.save(response);
  }

}
