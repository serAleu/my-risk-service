package asia.atmonline.myriskservice.data.risk.repositories.blacklists;

import asia.atmonline.myriskservice.data.risk.entity.blacklists.impl.BlacklistBankAccountRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.BaseRiskJpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistBankAccountRiskJpaRepository extends BaseRiskJpaRepository<BlacklistBankAccountRiskJpaEntity> {

  boolean existsByBankAccountInAndExpiredAtAfter(List<String> bankAccounts, LocalDateTime after);

  List<BlacklistBankAccountRiskJpaEntity> findByBankAccountAndBlReasonAndExpiredAtAfterOrderByAddedAtDesc(String account, String blReason, LocalDateTime after);

}
