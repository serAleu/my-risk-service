package asia.atmonline.myriskservice.data.repositories.impl.blacklists;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.BlackListRule;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRuleJpaRepository extends BaseJpaRepository<BlackListRule> {

  BlackListRule findByRuleId(Long ruleId);

}
