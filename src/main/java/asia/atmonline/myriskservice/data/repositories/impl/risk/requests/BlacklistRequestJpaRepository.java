package asia.atmonline.myriskservice.data.repositories.impl.risk.requests;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.BlacklistRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRequestJpaRepository extends BaseJpaRepository<BlacklistRequestJpaEntity> {}