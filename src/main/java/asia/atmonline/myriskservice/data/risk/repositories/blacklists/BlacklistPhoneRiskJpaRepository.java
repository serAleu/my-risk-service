package asia.atmonline.myriskservice.data.risk.repositories.blacklists;

import asia.atmonline.myriskservice.data.risk.entity.blacklists.BlacklistRule;
import asia.atmonline.myriskservice.data.risk.entity.blacklists.impl.BlacklistPhoneRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.BaseRiskJpaRepository;
import asia.atmonline.myriskservice.enums.risk.BlacklistSource;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistPhoneRiskJpaRepository extends BaseRiskJpaRepository<BlacklistPhoneRiskJpaEntity> {

  boolean existsByPhoneInAndExpiredAtAfter(List<String> phones, LocalDateTime after);

  boolean existsByPhoneInAndSourceInAndExpiredAtAfter(List<String> phones, List<BlacklistSource> sources, LocalDateTime after);

  List<BlacklistPhoneRiskJpaEntity> findByPhoneAndBlReasonAndExpiredAtAfterOrderByAddedAtDesc(String phone, BlacklistRule rule, LocalDateTime after);

  List<BlacklistPhoneRiskJpaEntity> findByPhoneAndExpiredAtAfterOrderByAddedAtDesc(String phone, LocalDateTime after);

}