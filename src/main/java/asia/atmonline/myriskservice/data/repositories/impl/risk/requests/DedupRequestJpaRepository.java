package asia.atmonline.myriskservice.data.repositories.impl.risk.requests;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.DedupRequestJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DedupRequestJpaRepository extends BaseJpaRepository<DedupRequestJpaEntity> {}