package asia.atmonline.myriskservice.data.storage.repositories.property;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryAddressCityJpaRepository extends BaseStorageJpaRepository<AddressCityDictionary> {

}
