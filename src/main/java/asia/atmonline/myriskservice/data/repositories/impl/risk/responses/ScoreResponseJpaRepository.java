package asia.atmonline.myriskservice.data.repositories.impl.risk.responses;

import asia.atmonline.myriskservice.data.entity.risk.responses.impl.ScoreResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreResponseJpaRepository extends BaseJpaRepository<ScoreResponseJpaEntity> {}