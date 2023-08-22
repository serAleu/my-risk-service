package asia.atmonline.myriskservice.data.storage.repositories.property;

import asia.atmonline.myriskservice.data.storage.entity.property.SystemProperty;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemPropertyJpaRepository extends BaseStorageJpaRepository<SystemProperty> {

  Optional<SystemProperty> findByPropertyKey(String key);

}
