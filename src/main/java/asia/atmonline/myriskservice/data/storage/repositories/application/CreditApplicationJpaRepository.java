package asia.atmonline.myriskservice.data.storage.repositories.application;

import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import asia.atmonline.myriskservice.enums.application.CreditApplicationStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditApplicationJpaRepository extends BaseStorageJpaRepository<CreditApplication> {

  List<CreditApplication> findByBorrowerId(Long borrowerId);

  Integer countByBorrowerIdInAndStatusIn(Set<Long> borrowerIds, List<CreditApplicationStatus> statuses);

  Integer countByBorrowerIdAndRequestedAtBetween(Long borrowerId, LocalDateTime start, LocalDateTime end);

}
