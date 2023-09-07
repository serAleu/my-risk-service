package asia.atmonline.myriskservice.data.storage.repositories.property;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryOccupationTypeJpaRepository extends BaseStorageJpaRepository<OccupationTypeDictionary> {

}
