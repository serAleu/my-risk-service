package asia.atmonline.myriskservice.data.repositories.impl.blacklists;

import asia.atmonline.myriskservice.data.risk.entity.blacklists.BlacklistRule;
import asia.atmonline.myriskservice.data.risk.repositories.BaseRiskJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRuleRiskJpaRepository extends BaseRiskJpaRepository<BlacklistRule> {

  BlacklistRule findByRuleId(Long ruleId);

}
