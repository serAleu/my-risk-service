package asia.atmonline.myriskservice.data.repositories.impl.risk;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskRequestJpaRepository extends BaseJpaRepository<RiskRequestJpaEntity> {

}
