package asia.atmonline.myriskservice.data.storage.repositories.property;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionaryWorkingIndustryJpaRepository extends BaseStorageJpaRepository<WorkingIndustryDictionary> {

}
