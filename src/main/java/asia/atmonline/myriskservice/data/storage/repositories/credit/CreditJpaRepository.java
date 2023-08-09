package asia.atmonline.myriskservice.data.storage.repositories.credit;

import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import asia.atmonline.myriskservice.enums.credit.CreditStatus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditJpaRepository extends BaseStorageJpaRepository<Credit> {

  @Query("select max(borrower.id) from Credit where id =?1")
  Long getBorrowerIdByCreditId(Long creditId);

  Credit findFirstByBorrowerIdAndStatusInOrderByIssuedAtDesc(Long borrowerId, Iterable<CreditStatus> statuses);

  Long countByBorrowerIdAndStatusIn(Long borrowerId, List<CreditStatus> statuses);

  Long countByBorrowerIdAndStatusInAndEnableSpecialProductOfferTrue(Long borrowerId, List<CreditStatus> statuses);

  Long countByBorrowerIdInAndStatusIn(Set<Long> borrowerIds, List<CreditStatus> statuses);

  @Query("select max(amount) from Credit where borrower.id =?1")
  Optional<BigDecimal> findMaxOfAmountByBorrowerId(Long borrowerId);

  @Query("select max(DPDMax) from Credit where borrower.id =?1")
  Optional<Long> findMaxOfDpdMaxByBorrowerId(Long borrowerId);

  @Query("select max(amount) from Credit where borrower.id =?1")
  Optional<Long> getMaxAmountByBorrowerId(Long borrowerId);

  Long countByBorrowerIdInAndDPDMaxGreaterThan(Set<Long> borrowerIds, Integer maxDpd);

  @Query("select count(cr) from Credit cr "
      + "join CreditProduct cp on cr.creditProductId = cp.id "
      + "where cr.borrower.id = ?1 and cp.code in ?2 and cr.status = 2000" )
  Long countFinishedCreditsByProductCodes(Long borrowerId, List<String> codes);

  @Query(value = "select cp.code from credit cr "
      + "join credit_product cp on cr.credit_product_id = cp.id "
      + "where cr.borrower_id = ?1 and cr.status = '2000' order by cr.issued_at desc limit 1", nativeQuery = true)
  String  findLastFinishedProductCode(Long borrowerId);

  Credit findByContractNumberIgnoreCase(String contractNumber);

  @Query(value = "select count(cr) from Credit cr "
      + "where cr.borrower_id = ?1 and cr.status = '2000'", nativeQuery = true)
  Integer finishedCreditsCount(Long borrowerId);

  @Query(value = "select count(cr) from Credit cr "
      + "where cr.borrower_id = ?1 and cr.status <> '2000'", nativeQuery = true)
  Integer notFinishedCreditsCount(Long borrowerId);
}
