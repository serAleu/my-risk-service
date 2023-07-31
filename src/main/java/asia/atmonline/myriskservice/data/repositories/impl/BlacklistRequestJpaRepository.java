package asia.atmonline.myriskservice.data.repositories.impl;

import asia.atmonline.myriskservice.data.entity.impl.requests.BlacklistRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRequestJpaRepository extends BaseJpaRepository<BlacklistRequestJpaEntity> {}