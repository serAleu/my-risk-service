package asia.atmonline.myriskservice.data.repositories.impl.risk.requests;

import asia.atmonline.myriskservice.data.entity.risk.responses.impl.CooldownResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CooldownRequestJpaRepository extends BaseJpaRepository<CooldownResponseJpaEntity> {}