package asia.atmonline.myriskservice.data.repositories.impl.blacklists;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistPassportNumberJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistPassportNumberJpaRepository extends BaseJpaRepository<BlacklistPassportNumberJpaEntity> {

  boolean existsByPassportNumberInAndExpiredAtAfter(List<String> passportNumbers,  LocalDateTime after);

  List<BlacklistPassportNumberJpaEntity> findByPassportNumberAndRuleIdAndExpiredAtAfterOrderByAddedAtDesc(String passportNumber, Long ruleId, LocalDateTime after);
}
