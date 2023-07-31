package asia.atmonline.myriskservice.data.repositories.impl;

import asia.atmonline.myriskservice.data.entity.impl.responses.BasicResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicResponseJpaRepository extends BaseJpaRepository<BasicResponseJpaEntity> {}