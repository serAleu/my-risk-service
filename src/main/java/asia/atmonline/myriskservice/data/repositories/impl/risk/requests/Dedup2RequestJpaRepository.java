package asia.atmonline.myriskservice.data.repositories.impl.risk.requests;

import asia.atmonline.myriskservice.data.entity.risk.responses.impl.Dedup2ResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Dedup2RequestJpaRepository extends BaseJpaRepository<Dedup2ResponseJpaEntity> {}