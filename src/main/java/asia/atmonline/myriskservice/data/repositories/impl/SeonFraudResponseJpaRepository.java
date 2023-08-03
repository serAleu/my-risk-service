package asia.atmonline.myriskservice.data.repositories.impl;

import asia.atmonline.myriskservice.data.entity.responses.impl.SeonFraudResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface SeonFraudResponseJpaRepository extends BaseJpaRepository<SeonFraudResponseJpaEntity> {

  Optional<SeonFraudResponseJpaEntity> findTop1ByBorrowerIdAndCreatedAtGreaterThanAndSuccessOrderByCreatedAtDesc(Long borrowerId, LocalDateTime date, Boolean isSuccess);
}