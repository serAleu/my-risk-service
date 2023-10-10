package asia.atmonline.myriskservice.services.dedup;

import static asia.atmonline.myriskservice.enums.application.CreditApplicationStatus.INITIAL;
import static asia.atmonline.myriskservice.enums.application.CreditApplicationStatus.OUTGOING_PAYMENT_SUCCEED;
import static asia.atmonline.myriskservice.enums.risk.CheckType.DEDUP;
import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.borrower.BorrowerAdditionalIdNumberJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.borrower.BorrowerAdditionalPhoneJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.borrower.BorrowerJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.credit.CreditJpaRepository;
import asia.atmonline.myriskservice.enums.application.CreditApplicationStatus;
import asia.atmonline.myriskservice.enums.credit.CreditStatus;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationContext;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeduplicationChecksService implements BaseRiskChecksService {

  private final List<? extends BaseDeduplicationRule<? extends BaseDeduplicationContext>> rules;
  private final BlacklistChecksService blacklistChecksService;
  private final BorrowerAdditionalPhoneJpaRepository borrowerAdditionalPhoneJpaRepository;
  private final BorrowerAdditionalIdNumberJpaRepository borrowerAdditionalIdNumberJpaRepository;
  private final BorrowerJpaRepository borrowerJpaRepository;
  private final CreditJpaRepository creditJpaRepository;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    return process(request, false);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request, boolean isFinalCheck) {
    RiskResponseJpaEntity response = getRiskResponseJpaEntity(request);
    Long borrowerId = creditApplicationJpaRepository.findBorrowerIdById(request.getApplicationId());
    Optional<Borrower> borrower = borrowerJpaRepository.findById(borrowerId);
    if (borrower.isPresent()) {
      boolean isBankAccountMatchedWithBlAccount = blacklistChecksService.checkBankAccountBlacklist(borrower.get().getBorrowerAccount());
      boolean isPassportNumMatchedWithBlIdNumber = blacklistChecksService.checkPassportNumberBlacklist(borrower.get().getBorrowerNIC());
      Set<Long> borrowerIds = getDuplicatedBorrowerIdsForPostPvRoute(borrower.get());
      Integer maxDpdCount = countDPDMaxMoreThan(borrowerIds);
      Integer notFinishedCreditsCount = countNotFinishedCredits(borrowerIds);
      Integer countInProgress = countInProgress(borrowerIds);
      Integer rejectedApplicationsCount = countByApplicationRejectedAndBorrowerIdIn(borrowerIds);
      Integer approvedApplicationsCount = countApproved(borrowerIds);
      for (BaseDeduplicationRule rule : rules) {
        response = rule.execute(
            rule.getContext(response, isFinalCheck, approvedApplicationsCount, rejectedApplicationsCount, countInProgress, notFinishedCreditsCount, maxDpdCount,
                isBankAccountMatchedWithBlAccount, isPassportNumMatchedWithBlIdNumber));
        if (response != null && REJECT.equals(response.getDecision())) {
          if (response.getRejectionReason() != null) {
            rule.saveToBlacklists(request.getApplicationId(), borrowerId, response.getRejectionReason());
          }
          return response;
        }
      }
    }
    return response;
  }

  @NotNull
  private RiskResponseJpaEntity getRiskResponseJpaEntity(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    response.setRequestId(request.getId());
    response.setApplicationId(request.getApplicationId());
    response.setCheckType(DEDUP);
    return response;
  }

  @Transactional(readOnly = true)
  public Integer countApproved(Set<Long> borrowerIds) {
    if (borrowerIds.isEmpty()) {
      return 0;
    }
    return creditApplicationJpaRepository.countByBorrowerIdInAndStatusIn(borrowerIds, List.of(OUTGOING_PAYMENT_SUCCEED.getCode()));
  }

  @Transactional(readOnly = true)
  public Integer countByApplicationRejectedAndBorrowerIdIn(Set<Long> borrowerIds) {
    return !borrowerIds.isEmpty() ? creditApplicationJpaRepository.countByApplicationRejectedAndBorrowerIdIn(borrowerIds,
        CreditApplicationStatus.REJECTED.getCode()) : 0;
  }

  @Transactional(readOnly = true)
  public Integer countInProgress(Set<Long> borrowerIds) {
    if (borrowerIds.isEmpty()) {
      return 0;
    }
    return creditApplicationJpaRepository.countByBorrowerIdInAndStatusIn(borrowerIds, List.of(INITIAL.getCode()));
  }

  @Transactional(readOnly = true)
  public Integer countNotFinishedCredits(Set<Long> borrowerIds) {
    if (borrowerIds.isEmpty()) {
      return 0;
    }
    return creditJpaRepository.countByBorrowerIdInAndStatusIn(borrowerIds, CreditStatus.getNotFinishedStatuses());
  }

  @Transactional(readOnly = true)
  public Integer countDPDMaxMoreThan(Set<Long> borrowerIds) {
    if (borrowerIds.isEmpty()) {
      return 0;
    }
//    return creditJpaRepository.countByBorrowerIdInAndDPDMaxGreaterThan(borrowerIds, DeduplicationMaxDpdContext.MAX_HISTORICAL_OVERDUE_DAYS);
    return 0;
  }

  @Transactional(readOnly = true)
  public Set<Long> getDuplicatedBorrowerIdsForPostPvRoute(Borrower borrower) {
    Set<Long> borrowerIds = new HashSet<>();
    borrowerIds.addAll(getBorrowerIdsByAdditionalPhone(borrower));
    borrowerIds.addAll(getBorrowerIdsByAdditionalNic(borrower));
    borrowerIds.addAll(getBorrowerIdsByConfirmedEmail(borrower));
    borrowerIds.remove(borrower.getId());
    return borrowerIds;
  }

  private Set<Long> getBorrowerIdsByConfirmedEmail(Borrower borrower) {
    Set<Long> borrowerIds = new HashSet<>();
    if(borrower.getPersonalData() != null && borrower.getPersonalData().getEmail() != null) {
      borrowerIds = borrowerJpaRepository.findBorrowerIdsByPersonalDataPdEmail(List.of(borrower.getPersonalData().getEmail()));
      if (!borrowerIds.isEmpty()) {
        borrowerIds.remove(borrower.getId());
      }
    }
    return borrowerIds;
  }

  private Set<Long> getBorrowerIdsByAdditionalNic(Borrower borrower) {
    Set<Long> borrowerIds = new HashSet<>();
    List<String> additionalNics = borrowerAdditionalIdNumberJpaRepository.findNicsByBorrowerId(borrower.getId());
    if (!additionalNics.isEmpty()) {
      Set<Long> borrowerIdsFromAdditionalNics = borrowerAdditionalIdNumberJpaRepository.findBorrowerIdsByNics(additionalNics);
      Set<Long> borrowerIdsFromMainNics = borrowerJpaRepository.findIdsByNics(additionalNics);
      borrowerIds.addAll(borrowerIdsFromAdditionalNics);
      borrowerIds.addAll(borrowerIdsFromMainNics);
      borrowerIds.remove(borrower.getId());
    }
    return borrowerIds;
  }

  private Set<Long> getBorrowerIdsByAdditionalPhone(Borrower borrower) {
    Set<Long> borrowerIds = new HashSet<>();
    List<String> additionalPhones = borrowerAdditionalPhoneJpaRepository.findPhonesByBorrowerId(borrower.getId());
    if (!additionalPhones.isEmpty()) {
      Set<Long> borrowerIdsFromAdditionalPhones = borrowerAdditionalPhoneJpaRepository.findBorrowerIdsByPhones(additionalPhones);
      List<Long> borrowerIdsFromMainPhones = borrowerJpaRepository.findIdsByPhoneNumbers(additionalPhones);
      borrowerIds.addAll(borrowerIdsFromAdditionalPhones);
      borrowerIds.addAll(borrowerIdsFromMainPhones);
      borrowerIds.remove(borrower.getId());
    }
    return borrowerIds;
  }
}