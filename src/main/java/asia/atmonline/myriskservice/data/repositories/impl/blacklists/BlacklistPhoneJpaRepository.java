package asia.atmonline.myriskservice.data.repositories.impl.blacklists;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistPhoneJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.enums.risk.BlacklistSource;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistPhoneJpaRepository extends BaseJpaRepository<BlacklistPhoneJpaEntity> {

  boolean existsByPhoneInAndExpiredAtAfter(List<String> phones, LocalDateTime after);

  boolean existsByPhoneInAndSourceInAndExpiredAtAfter(List<String> phones, List<BlacklistSource> sources, LocalDateTime after);

  List<BlacklistPhoneJpaEntity> findByPhoneAndRuleIdAndExpiredAtAfterOrderByAddedAtDesc(String phone, Long ruleId, LocalDateTime after);

  List<BlacklistPhoneJpaEntity> findByPhoneAndExpiredAtAfterOrderByAddedAtDesc(String phone, LocalDateTime after);

}