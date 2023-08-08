package asia.atmonline.myriskservice.data.repositories.impl.risk.requests;

import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.BasicRequestJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicRequestJpaRepository extends BaseJpaRepository<BasicRequestJpaEntity> {}