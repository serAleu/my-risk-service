package asia.atmonline.myriskservice.data.storage.repositories.property;

import asia.atmonline.myriskservice.data.storage.entity.property.impl.SystemProperty;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemPropertyJpaRepository extends BaseStorageJpaRepository<SystemProperty> {

//  Optional<SystemProperty> findByPropertyKey(String key);

}
