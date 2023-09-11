package asia.atmonline.myriskservice.data.storage.repositories.application;

import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import asia.atmonline.myriskservice.enums.application.CreditApplicationStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditApplicationJpaRepository extends BaseStorageJpaRepository<CreditApplication> {

  @Query("select borrower.id from CreditApplication where id = ?1")
  Long findBorrowerIdById(Long id);

  @Query(value = "select count(distinct borrower.id)" +
      " from CreditApplication " +
      " where borrower.id in (?1) and status = ?2")
  Integer countByApplicationRejectedAndBorrowerIdIn(Set<Long> borrowerIds, CreditApplicationStatus status);

  @Query(value = "select status from \"my-back\".credit_application where borrower_id = :borrowerId", nativeQuery = true)
  List<Long> findAllCreditApplicationStatusesByBorrowerId(@Param("borrowerId") Long borrowerId);

  Integer countByBorrowerIdInAndStatusIn(Set<Long> borrowerIds, List<CreditApplicationStatus> statuses);

  Integer countByBorrowerIdAndRequestedAtBetween(Long borrowerId, LocalDateTime start, LocalDateTime end);

}
