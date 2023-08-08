package asia.atmonline.myriskservice.data.repositories.impl.blacklists;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistBankAccountJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.enums.risk.BlacklistSource;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistBankAccountJpaRepository extends BaseJpaRepository<BlacklistBankAccountJpaEntity> {

  boolean existsByBankAccountInAndSourceInAndExpiredAtAfter(List<String> bankAccounts, List<BlacklistSource> sources, LocalDateTime after);

  List<BlacklistBankAccountJpaEntity> findByBankAccountAndRuleIdAndExpiredAtAfterOrderByAddedAtDesc(String idNumber, Long ruleId, LocalDateTime after);

}
