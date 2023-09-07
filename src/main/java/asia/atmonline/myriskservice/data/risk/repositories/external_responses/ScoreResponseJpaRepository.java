package asia.atmonline.myriskservice.data.risk.repositories.external_responses;

import asia.atmonline.myriskservice.data.risk.entity.external_responses.ScoreResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.BaseRiskJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreResponseJpaRepository extends BaseRiskJpaRepository<ScoreResponseRiskJpaEntity> {}