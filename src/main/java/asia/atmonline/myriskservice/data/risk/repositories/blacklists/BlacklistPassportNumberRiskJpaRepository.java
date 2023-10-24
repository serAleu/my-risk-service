package asia.atmonline.myriskservice.data.risk.repositories.blacklists;

import asia.atmonline.myriskservice.data.risk.entity.blacklists.BlacklistRule;
import asia.atmonline.myriskservice.data.risk.entity.blacklists.impl.BlacklistPassportNumberRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.BaseRiskJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistPassportNumberRiskJpaRepository extends BaseRiskJpaRepository<BlacklistPassportNumberRiskJpaEntity> {

  boolean existsByPassportNumberInAndExpiredAtAfter(List<String> passportNumbers,  LocalDateTime after);

  List<BlacklistPassportNumberRiskJpaEntity> findByPassportNumberAndBlReasonAndExpiredAtAfterOrderByAddedAtDesc(String passportNumber, BlacklistRule rule, LocalDateTime after);
}
