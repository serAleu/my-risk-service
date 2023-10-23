package asia.atmonline.myriskservice.data.risk.repositories.blacklists;

import asia.atmonline.myriskservice.data.risk.entity.blacklists.BlacklistRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRuleRiskJpaRepository extends JpaRepository<BlacklistRule, String> {}
