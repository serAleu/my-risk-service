package asia.atmonline.myriskservice.data.storage.entity.credit;

import asia.atmonline.myriskservice.data.storage.entity.BaseCreditEntity;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.enums.credit.CreditStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SortNatural;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "credit", schema = "my-back")
@Getter
@Setter
public class Credit extends BaseCreditEntity {

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "credit_application_id", nullable = false)
  private CreditApplication creditApplication;

  @Column(name = "amount", precision = 19, nullable = false)
  private BigDecimal amount;

  @Column(name = "term", nullable = false)
  private Integer term;

  @Column(name = "issued_at")
  private LocalDateTime issuedAt;

  @Column(name = "finished_at")
  private LocalDate finishedAt;

  @Column(name = "status", nullable = false)
  private CreditStatus status;

  @Column(name = "sum_of_manual_adjustments")
  private BigDecimal sumOfManualAdjustments;


  @OrderBy("date DESC")
  @BatchSize(size = DEFAULT_BATCH_SIZE)
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CreditSnapshot> creditSnapshots;

  @JsonIgnore
  @Where(clause = "actual = 'true'")
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "credit", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CreditSnapshot> actualCreditSnapshot;

  @SortNatural
//  @OrderBy(value = "periodStart")
  @BatchSize(size = DEFAULT_BATCH_SIZE)
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "credit_id")
  private Set<CreditInstallment> installments;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "credit_id")
  @OrderBy(value = "createdAt")
  private List<CreditRestructuring> restructurings;

  @Column(name = "need_upload_ch", nullable = false)
  private boolean needUploadCH = false;

  @OrderBy(value = "createdAt DESC")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(inverseJoinColumns = @JoinColumn(name = "promisedPayment_id"))
  @BatchSize(size = DEFAULT_BATCH_SIZE)
  private Set<PromisedPayment> promisedPayments;

  @Column(name = "sum_of_auto_adjustments")
  private BigDecimal sumOfAutoAdjustments;

  @Column(name = "dpd_max", nullable = false, columnDefinition = "integer default 0")
  private Integer DPDMax;

  @Column(name = "dpd_current", nullable = false, columnDefinition = "integer default 0")
  private Integer DPDCurrent;

  @Column(name = "restructured_count")
  private Integer restructuredCount = 0;

  @Column(name = "paid_real_money", precision = 19, nullable = false, columnDefinition="Decimal(19,2) default '0.00'")
  private BigDecimal paidRealMoney;

  @Column(name = "grace_period")
  private Boolean gracePeriod;

  @Column(name = "grace_period_end_date")
  private LocalDate gracePeriodEndDate;

  @Column(name = "early_repayment")
  private boolean earlyRepayment;

  @Column(name = "enable_special_product_offer")
  private boolean enableSpecialProductOffer;

//  @Transient
//  public BigDecimal getGracePeriodErf() {
//    BigDecimal result = BigDecimal.ZERO;
//    BigDecimal gracePeriodErfPct = this.getCreditProduct().getCalculationSettings().getGracePeriodErf();
//    if (this.isGracePeriodActive()) {
//      result = this.getAmount().multiply(gracePeriodErfPct).divide(ONE_HUNDRED);
//    }
//    return result.setScale(2, RoundingMode.HALF_UP);
//  }

//  @Transient
//  public boolean isGracePeriodActive() {
//    BigDecimal gracePeriodErfPct = this.getCreditProduct().getCalculationSettings().getGracePeriodErf();
//    if (this.getCreditProduct().isGracePeriodActive() && gracePeriodErfPct != null
//                   && !this.getGracePeriodEndDate().isBefore(LocalDate.now())) {
//      return true;
//    }
//    return false;
//  }

//  @Transient
//  public BigDecimal getGracePeriodPayToCloseAmount() {
//    BigDecimal result = null;
//    if (this.isGracePeriodActive()) {
//      BigDecimal paidAmount = BigDecimal.ZERO;
//      if (this.getActualSnapshot() != null) {
//        paidAmount = this.getActualSnapshot().getBalance().add(this.getActualSnapshot().getPaidTotal());
//      }
//      result = this.getAmount().add(this.getGracePeriodErf()).subtract(paidAmount);
//    }
//    return result.setScale(2, RoundingMode.HALF_UP);
//  }

//  @Transient
//  public BigDecimal getGracePeriodSavingAmount() {
//    BigDecimal result = BigDecimal.ZERO;
//    for (CreditInstallment installment : this.installments) {
//      result = result.add(installment.getAmount().add(installment.getServiceFee().add(installment.getProcessingFee())));
//    }
//    result = result.subtract(this.getAmount().add(this.getGracePeriodErf()));
//    return result.setScale(2, RoundingMode.HALF_UP);
//  }

  @Transient
  public CreditInstallment getInstallment(final int idx) {
    Set<CreditInstallment> installmentSet = this.getInstallments();
    if (CollectionUtils.isEmpty(installmentSet)) {
      return null;
    }
    Iterator<CreditInstallment> installmentIterator = installmentSet.iterator();
    int idxCounter = 0;
    while (installmentIterator.hasNext()) {
      CreditInstallment installment = installmentIterator.next();
      if (idxCounter++ == idx) {
        return installment;
      }
    }
    return null;
  }

  @Transient
  public LocalDate getEndDate() {
    LocalDate endDate = null;
    if (installments != null && !installments.isEmpty()) {
      for (CreditInstallment installment : installments) {
        endDate = installment.getPeriodEnd();
      }
    }
    return endDate;
  }

  @Transient
  public BigDecimal getTotalInstallmentAmount(int unpaidInstallmentIdx) {
    Set<CreditInstallment> installmentSet = this.getInstallments();
    if (CollectionUtils.isEmpty(installmentSet)) {
      return null;
    }
    Iterator<CreditInstallment> installmentIterator = installmentSet.iterator();
    int idxCounter = 0;
    BigDecimal amount = BigDecimal.ZERO;
    while (installmentIterator.hasNext()) {
      CreditInstallment installment = installmentIterator.next();
      if (idxCounter++ >= unpaidInstallmentIdx) {
        amount = amount.add(installment.getAmount());
      }
    }
    return amount;
  }

  @Transient
  public BigDecimal getInstallmentsAmount(int unpaidInstallmentIdx, int currentInstallmentIdx) {
    Set<CreditInstallment> installmentSet = this.getInstallments();
    if (CollectionUtils.isEmpty(installmentSet)) {
      return null;
    }
    Iterator<CreditInstallment> installmentIterator = installmentSet.iterator();
    int idxCounter = 0;
    BigDecimal amount = BigDecimal.ZERO;
    while (installmentIterator.hasNext()) {
      CreditInstallment installment = installmentIterator.next();
      if (idxCounter >= unpaidInstallmentIdx && idxCounter <= currentInstallmentIdx) {
        amount = amount.add(installment.getAmount());
      }
      idxCounter++;
    }
    return amount;
  }

  @Transient
  public CreditSnapshot getActualSnapshot() {
    return getCreditSnapshots().stream().filter(CreditSnapshot::isActual).findFirst().orElse(null);
  }

  @Transient
  public int getMaxDaysOverdue() {
    return getCreditSnapshots().stream().mapToInt(CreditSnapshot::getDaysOverdue).max().orElse(0);
  }

  @Transient
  public LocalDate getInitialExpirationDate() {
    LocalDate issueDate = getIssuedAt().toLocalDate();
    Integer initialTerm = getTerm();
    return issueDate.plusDays(initialTerm);
  }

//  @JsonIgnore
//  @Transient
//  public long getTerm(ChronoUnit unit) {
//    LocalDateTime from = this.getIssuedAt();
//    if (from == null) {
//      from = this.getCreditApplication().getRequestedAt();
//    }
//    if (from == null) {
//      from = LocalDateTime.now();
//    }
//    LocalDateTime to = from;
//    to = to.plus(this.getTerm(), this.getCreditProduct().getTermUnit());
//    return unit.between(from, to);
//  }

  @Transient
  public boolean needApplyGracePeriodRule(LocalDate currentSnapshotDate) {
    return Boolean.TRUE.equals(gracePeriod)
            && currentSnapshotDate.isBefore(gracePeriodEndDate.plusDays(1));
  }

  @Transient
  public boolean needApplyEarlyRepaymentRule(CreditSnapshot currentSnapshot) {
    boolean isLastInstallmentAndExpired = currentSnapshot.getCurrentInstallment().equals(getTerm() - 1)
            && CreditStatus.EXPIRED == currentSnapshot.getStatus();
    boolean isRestructuringNotApplied = getRestructuredCount() == 0;
    return isEarlyRepayment()
            && isRestructuringNotApplied
            && !isLastInstallmentAndExpired
            && !needApplyGracePeriodRule(currentSnapshot.getDate());
  }
}

