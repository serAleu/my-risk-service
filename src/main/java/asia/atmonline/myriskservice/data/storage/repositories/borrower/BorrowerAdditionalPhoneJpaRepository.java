package asia.atmonline.myriskservice.data.storage.repositories.borrower;

import asia.atmonline.myriskservice.data.storage.entity.borrower.BorrowerAdditionalPhone;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import asia.atmonline.myriskservice.enums.borrower.BorrowerAdditionalPhoneType;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerAdditionalPhoneJpaRepository extends BaseStorageJpaRepository<BorrowerAdditionalPhone> {

  @Query("select value from BorrowerAdditionalPhone where borrowerId = :borrowerId")
  List<String> findPhonesByBorrowerId(@Param("borrowerId") Long borrowerId);

  @Query("select borrowerId from BorrowerAdditionalPhone where value in :phones")
  Set<Long> findBorrowerIdsByPhones(@Param("phones") List<String> phones);

  boolean existsByBorrowerIdAndValue(Long borrowerId, String phoneNumber);

  List<BorrowerAdditionalPhone> findByBorrowerIdAndType(Long borrowerId, BorrowerAdditionalPhoneType phoneType);

}
