package asia.atmonline.myriskservice.data.storage.repositories.borrower;

import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerJpaRepository extends BaseStorageJpaRepository<Borrower> {

}
