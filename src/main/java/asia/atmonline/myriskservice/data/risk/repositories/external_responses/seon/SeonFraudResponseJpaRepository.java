package asia.atmonline.myriskservice.data.risk.repositories.external_responses.seon;

import asia.atmonline.myriskservice.data.risk.entity.external_responses.seon.SeonFraudResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.BaseRiskJpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface SeonFraudResponseJpaRepository extends BaseRiskJpaRepository<SeonFraudResponseRiskJpaEntity> {

  Optional<SeonFraudResponseRiskJpaEntity> findTop1ByBorrowerIdAndCreatedAtGreaterThanAndSuccessOrderByCreatedAtDesc(Long borrowerId, LocalDateTime date, Boolean isSuccess);
}