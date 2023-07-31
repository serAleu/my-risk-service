package asia.atmonline.myriskservice.data.repositories.impl;

import asia.atmonline.myriskservice.data.repositories.BaseJpaRepository;
import asia.atmonline.myriskservice.data.entity.impl.requests.BasicRequestJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicRequestJpaRepository extends BaseJpaRepository<BasicRequestJpaEntity> {}