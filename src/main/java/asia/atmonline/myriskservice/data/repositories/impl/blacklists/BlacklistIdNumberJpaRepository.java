package asia.atmonline.myriskservice.data.repositories.impl.blacklists;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistIdNumberJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.enums.risk.BlacklistSource;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistIdNumberJpaRepository extends BaseJpaRepository<BlacklistIdNumberJpaEntity> {

  boolean existsByIdNumberInAndSourceInAndExpiredAtAfter(List<String> cmnds, List<BlacklistSource> sources, LocalDateTime after);

  List<BlacklistIdNumberJpaEntity> findByIdNumberAndRuleIdAndExpiredAtAfterOrderByAddedAtDesc(String idNumber, Long ruleId, LocalDateTime after);
}
