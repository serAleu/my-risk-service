package asia.atmonline.myriskservice.data.repositories.impl;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseJpaRepository<T extends BaseJpaEntity> extends JpaRepository<T, Long> {}