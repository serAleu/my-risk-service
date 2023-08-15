package asia.atmonline.myriskservice.data.repositories.impl.blacklists;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistBankAccountJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistBankAccountJpaRepository extends BaseJpaRepository<BlacklistBankAccountJpaEntity> {

  boolean existsByBankAccountInAndExpiredAtAfter(List<String> bankAccounts, LocalDateTime after);

  List<BlacklistBankAccountJpaEntity> findByBankAccountAndRuleIdAndExpiredAtAfterOrderByAddedAtDesc(String account, Long ruleId, LocalDateTime after);

}
