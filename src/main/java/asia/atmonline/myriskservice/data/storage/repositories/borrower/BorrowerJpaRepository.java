package asia.atmonline.myriskservice.data.storage.repositories.borrower;

import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import asia.atmonline.myriskservice.enums.application.ApplicationsStep;
import asia.atmonline.myriskservice.enums.borrower.LoanPurpose;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowerJpaRepository extends BaseStorageJpaRepository<Borrower> {

  Optional<Borrower> findBorrowerByPersonalDataMobilePhone(String phone);

//  @Query("select id from borrower where personalData.pdEmail in :pdEmails")
//  Set<Long> findBorrowerIdsByPersonalDataPdEmail(@Param("pdEmails") List<String> pdEmails);

  @Query("select id from Borrower where personalData.mobilePhone in :phoneNumbers")
  List<Long> findIdsByPhoneNumbers(@Param("phoneNumbers") List<String> phoneNumbers);

//  @Query("select distinct id "
//      + "from Borrower "
//      + "where contactPerson1.phone = :phoneNumber "
//      + "or contactPerson2.phone = :phoneNumber")
//  List<Long> findBorrowerIdsByCpPhone(@Param("phoneNumber") String phoneNumber);

  @Query(nativeQuery = true, value = "select id from borrower " +
      "where replace(upper(pd_first_name || pd_second_name || pd_last_name), ' ', '') = ?1 " +
      "and pd_birth_date = ?2")
  Set<Long> findBorrowerIdsByFullNameAndBirthDate(String fullName, LocalDate birthDate);

  @Query(value = "select id from Borrower where identityData.primaryId = ?1")
  Set<Long> findIdByIdentityData_PrimaryId(String nic);

  @Query("select id from Borrower where bankDetailsData.accountNumber = :accountNumber")
  Set<Long> findIdsByAccountNumber(@Param("accountNumber") String accountNumber);

  @Query("select id from Borrower where identityData.primaryId in :nics")
  Set<Long> findIdsByNics(@Param("nics") List<String> nics);

  @Query("select b.loanPurpose from Borrower b where b.id = ?1")
  Optional<LoanPurpose> findLoanPurpose(final Long borrowerId);

  @Modifying
  @Query("update Borrower b "
      + "set b.lastApplicationStep = :applicationsStep, "
      + "b.lastApplicationStepUpdatedAt = :updatedAt "
      + "where b.id = :borrowerId")
  void updateLastApplicationsStep(
      @Param("borrowerId") Long borrowerId,
      @Param("applicationsStep") ApplicationsStep applicationsStep,
      @Param("updatedAt") LocalDateTime updatedAt);

  @Modifying
  @Query("update Borrower b "
      + "set b.lastApplicationStep = :applicationsStep "
      + "where b.id = :borrowerId")
  void updateLastApplicationsStep(
      @Param("borrowerId") Long borrowerId,
      @Param("applicationsStep") ApplicationsStep applicationsStep
  );

  @Query(value = "select count(distinct b.id)" +
      "  from borrower b join credit_application ca on b.id = ca.borrower_id" +
      "  where b.id in (?1) and ca.status = ?2", nativeQuery = true)
  Integer countByApplicationRejectedAndBorrowerIdIn(Set<Long> borrowerIds, int rejectCode);

}
