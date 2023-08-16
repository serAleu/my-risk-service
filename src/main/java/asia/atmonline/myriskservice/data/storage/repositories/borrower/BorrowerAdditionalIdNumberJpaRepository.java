package asia.atmonline.myriskservice.data.storage.repositories.borrower;

import asia.atmonline.myriskservice.data.storage.entity.borrower.BorrowerAdditionalIdNumber;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerAdditionalIdNumberJpaRepository extends BaseStorageJpaRepository<BorrowerAdditionalIdNumber> {

  @Query("select borrowerId from BorrowerAdditionalIdNumber where value in :nics")
  Set<Long> findBorrowerIdsByNics(@Param("nics") List<String> nics);

  @Query("select value from BorrowerAdditionalIdNumber where borrowerId = :borrowerId")
  List<String> findNicsByBorrowerId(@Param("borrowerId") Long borrowerId);
}
