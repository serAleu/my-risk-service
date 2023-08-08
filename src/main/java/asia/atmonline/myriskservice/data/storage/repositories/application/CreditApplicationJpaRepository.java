package asia.atmonline.myriskservice.data.storage.repositories.application;

import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditApplicationJpaRepository extends BaseStorageJpaRepository<CreditApplication> {

}
