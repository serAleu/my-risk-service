package asia.atmonline.myriskservice.data.risk.repositories;

import asia.atmonline.myriskservice.data.risk.entity.BaseRiskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRiskJpaRepository<T extends BaseRiskJpaEntity> extends JpaRepository<T, Long> {}