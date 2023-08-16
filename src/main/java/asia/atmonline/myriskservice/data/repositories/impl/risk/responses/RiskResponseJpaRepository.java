package asia.atmonline.myriskservice.data.repositories.impl.risk.responses;

import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskResponseJpaRepository  extends BaseJpaRepository<RiskResponseJpaEntity<? extends BaseSqsProducer>> {}