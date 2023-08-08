package asia.atmonline.myriskservice.data.storage.entity.credit;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import asia.atmonline.myriskservice.enums.credit.CreditStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "credit_snapshot")
public class CreditSnapshot extends BaseStorageEntity {

  @Transient
  private Long creditId;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "credit_id", updatable = false)
  private Credit credit;

  @Column(name = "credit_status", nullable = false)
  private CreditStatus status;

  @Column(name = "status_date")
  private LocalDate statusDate;

  @Column(name = "actual", nullable = false)
  private boolean actual;

  @Column(name = "date", nullable = false)
  private LocalDate date;

  @Column(name = "days_overdue")
  private Integer daysOverdue;

  @Column(name = "current_installment")
  private Integer currentInstallment;

  @Column(name = "balance", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal balance;

  @Column(name = "refunded_amount", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal refundedAmount;

  /**
   * Основной долг
   */
  @Column(name = "principal", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal principal;

  @Column(name = "overdue_principal", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal overduePrincipal;

  @Column(name = "paid_principal", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal paidPrincipal;

  /**
   * Начисленный штраф
   */
  @Column(name = "latePaymentFee", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal latePaymentFee;

  @Column(name = "paid_latePaymentFee", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal paidLatePaymentFee;

  /**
   * Сверхначисленные проценты
   */
  @Column(name = "overcharged_interest", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal overchargedInterest;

  @Column(name = "paid_overcharged_interest", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal paidOverchargedInterest;


  /**
   * Основные проценты %
   */
  @Column(name = "interest", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal interest;

  @Column(name = "overdue_interest", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal overdueInterest;

  @Column(name = "paid_interest", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal paidInterest;

  /**
   * Flat % of original loan amount for every month. Payable every month together with pricipal and interests.
   */
  @Column(name = "service_fee", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal serviceFee;

  @Column(name = "overdue_service_fee", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal overdueServiceFee;

  @Column(name = "paid_service_fee", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal paidServiceFee;

  /**
   * Flat % of original loan amount for every month. Payable every month together with pricipal and interests.
   */
  @Column(name = "consulting_fee", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal consultingFee;

  @Column(name = "overdue_consulting_fee", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal overdueConsultingFee;

  @Column(name = "paid_consulting_fee", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal paidConsultingFee;


  @Column(name = "end_of_period")
  private LocalDate endOfPeriod;

  @Column(name = "repayment_amount", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal repaymentAmount;

  @Column(name = "scheduled_payment", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal scheduledPayment;

  @Column(name = "total_paid_amount", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal totalPaidAmount;

  @Column(name = "total_overdue_amount", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal totalOverdueAmount;

  @Column(name = "applied_balance_date")
  private LocalDate appliedBalanceDate;

  @Column(name = "paid_installments", columnDefinition = "jsonb default '{}'")
  private List<Integer> paidInstallments;

  @Column(name = "restructuring_fee", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal restructuringFee;

  @Column(name = "paid_restructuring_fee", scale = DEFAULT_AMOUNT_SCALE)
  private BigDecimal paidRestructuringFee;

  @Transient
  private BigDecimal sumOfAutoAdjustments;

  @Embedded
  private EarlyRepaymentSnapshotData earlyRepaymentData;

  public CreditSnapshot() {
  }

  public CreditSnapshot(Credit credit, CreditStatus status, LocalDate statusDate, LocalDate date,
      BigDecimal balance, BigDecimal refundedAmount,  BigDecimal latePaymentFee,
      BigDecimal paidPrincipal, BigDecimal paidInterest, BigDecimal paidLatePaymentFee,
      BigDecimal principal, BigDecimal interest,
      BigDecimal overduePrincipal, BigDecimal overchargedInterest, Integer daysOverdue,
      Integer currentInstallment, BigDecimal serviceFee, BigDecimal paidServiceFee, BigDecimal consultingFee,
      BigDecimal paidConsultingFee,
      BigDecimal overdueConsultingFee, BigDecimal overdueServiceFee, BigDecimal overdueInterest,
      BigDecimal repaymentAmount, BigDecimal scheduledPayment, LocalDate endOfPeriod,
      BigDecimal paidOverchargedInterest, BigDecimal restructuringFee, BigDecimal paidRestructuringFee,
      EarlyRepaymentSnapshotData earlyRepaymentData) {
    this.credit = credit;
    this.status = status;
    this.statusDate = statusDate;
    this.date = date;
    this.balance = balance;
    this.refundedAmount = refundedAmount;
    this.latePaymentFee = latePaymentFee;
    this.paidPrincipal = paidPrincipal;
    this.paidInterest = paidInterest;
    this.paidLatePaymentFee = paidLatePaymentFee;
    this.principal = principal;
    this.interest = interest;
    this.overduePrincipal = overduePrincipal;
    this.overchargedInterest = overchargedInterest;
    this.daysOverdue = daysOverdue;
    this.currentInstallment = currentInstallment;
    this.serviceFee = serviceFee;
    this.paidServiceFee = paidServiceFee;
    this.consultingFee = consultingFee;
    this.paidConsultingFee = paidConsultingFee;
    this.overdueConsultingFee = overdueConsultingFee;
    this.overdueServiceFee = overdueServiceFee;
    this.overdueInterest = overdueInterest;
    this.repaymentAmount = repaymentAmount;
    this.scheduledPayment = scheduledPayment;
    this.endOfPeriod = endOfPeriod;
    this.paidOverchargedInterest = paidOverchargedInterest;
    this.restructuringFee = restructuringFee;
    this.paidRestructuringFee = paidRestructuringFee;
    this.restructuringFee = restructuringFee;
    this.paidRestructuringFee = paidRestructuringFee;
    this.earlyRepaymentData = earlyRepaymentData;
  }

  public CreditSnapshot(Credit credit, CreditStatus status, LocalDate statusDate, LocalDate date,
                        BigDecimal balance, BigDecimal refundedAmount, BigDecimal latePaymentFee,
                        BigDecimal paidPrincipal, BigDecimal paidInterest, BigDecimal paidLatePaymentFee,
                        BigDecimal principal, BigDecimal interest,
                        BigDecimal overduePrincipal, BigDecimal overchargedInterest, Integer daysOverdue,
                        Integer currentInstallment, BigDecimal serviceFee, BigDecimal paidServiceFee, BigDecimal consultingFee,
                        BigDecimal paidConsultingFee,
                        BigDecimal overdueConsultingFee, BigDecimal overdueServiceFee, BigDecimal overdueInterest,
                        BigDecimal repaymentAmount, BigDecimal scheduledPayment, LocalDate endOfPeriod, BigDecimal paidOverchargedInterest,
                        List<Integer> paidInstallments, BigDecimal restructuringFee, BigDecimal paidRestructuringFee,
                        EarlyRepaymentSnapshotData earlyRepaymentData) {
    this.credit = credit;
    this.status = status;
    this.statusDate = statusDate;
    this.date = date;
    this.balance = balance;
    this.refundedAmount = refundedAmount;
    this.latePaymentFee = latePaymentFee;
    this.paidPrincipal = paidPrincipal;
    this.paidInterest = paidInterest;
    this.paidLatePaymentFee = paidLatePaymentFee;
    this.principal = principal;
    this.interest = interest;
    this.overduePrincipal = overduePrincipal;
    this.overchargedInterest = overchargedInterest;
    this.daysOverdue = daysOverdue;
    this.currentInstallment = currentInstallment;
    this.serviceFee = serviceFee;
    this.paidServiceFee = paidServiceFee;
    this.consultingFee = consultingFee;
    this.paidConsultingFee = paidConsultingFee;
    this.overdueConsultingFee = overdueConsultingFee;
    this.overdueServiceFee = overdueServiceFee;
    this.overdueInterest = overdueInterest;
    this.repaymentAmount = repaymentAmount;
    this.scheduledPayment = scheduledPayment;
    this.endOfPeriod = endOfPeriod;
    this.paidOverchargedInterest = paidOverchargedInterest;
    this.paidInstallments = paidInstallments;
    this.restructuringFee = restructuringFee;
    this.paidRestructuringFee = paidRestructuringFee;
    this.earlyRepaymentData = earlyRepaymentData;
  }

  public static CreditSnapshot createInitial(Credit credit, LocalDate date) {
    return new CreditSnapshot(
        credit,
        CreditStatus.ACTIVE,
        date,
        date,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        0,
        0,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        null,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        BigDecimal.ZERO,
        new EarlyRepaymentSnapshotData()
                .setFee(BigDecimal.ZERO)
                .setPaidFee(BigDecimal.ZERO)
                .setCloseAmount(BigDecimal.ZERO)
                .setSavingAmount(BigDecimal.ZERO));
  }

  public CreditSnapshot copy(LocalDate date, Credit credit) {
    return new CreditSnapshot(
        credit,
        this.getStatus(),
        this.getStatusDate(),
        date,
        this.getBalance(),
        this.getRefundedAmount(),
        this.getLatePaymentFee(),
        this.getPaidPrincipal(),
        this.getPaidInterest(),
        this.getPaidLatePaymentFee(),
        this.getPrincipal(),
        this.getInterest(),
        this.getOverduePrincipal(),
        this.getOverchargedInterest(),
        this.getDaysOverdue(),
        this.getCurrentInstallment(),
        this.getServiceFee(),
        this.getPaidServiceFee(),
        this.getConsultingFee(),
        this.getPaidConsultingFee(),
        this.getOverdueConsultingFee(),
        this.getOverdueServiceFee(),
        this.getOverdueInterest(),
        this.getRepaymentAmount(),
        this.getScheduledPayment(),
        this.getEndOfPeriod(),
        this.getPaidOverchargedInterest(),
        this.getPaidInstallments(),
        this.getRestructuringFee(),
        this.getPaidRestructuringFee(),
        this.getEarlyRepaymentData());
  }

  @Transient
  public BigDecimal getPaidTotal() {
    return getTotalPaidWithoutRestructuring()
            .add(getPaidRestructuringFee());
  }

  @Transient
  public BigDecimal getTotalPaidWithoutRestructuring() {
    return getPaidPrincipal()
            .add(getPaidInterest())
            .add(getPaidLatePaymentFee())
            .add(getPaidOverchargedInterest())
            .add(getPaidConsultingFee())
            .add(getPaidServiceFee());
  }


  @SuppressWarnings("unused")
  @Transient
  public BigDecimal getTotalOverdueInterest() {
    return getOverchargedInterest().add(getOverdueInterest());
  }

  @SuppressWarnings("unused")
  @Transient
  public BigDecimal getTotalInterest() {
    return getInterest().add(getOverdueInterest());
  }

  @SuppressWarnings("unused")
  @Transient
  public BigDecimal getTotalConsultingFee() {
    return getConsultingFee().add(getOverdueConsultingFee());
  }

  @SuppressWarnings("unused")
  @Transient
  public BigDecimal getTotalServiceFee() {
    return getServiceFee().add(getOverdueServiceFee());
  }

  @SuppressWarnings("unused")
  @Transient
  public BigDecimal getTotalOverdue() {
    return getOverdueConsultingFee()
        .add(getOverdueServiceFee())
        .add(getOverduePrincipal())
        .add(getOverchargedInterest())
        .add(getLatePaymentFee())
        .add(getOverdueInterest());
  }

  @Transient
  public BigDecimal getTotalOverdueWithoutLatePaymentFee() {
    return getOverdueConsultingFee()
            .add(getOverdueServiceFee())
            .add(getOverduePrincipal())
            .add(getOverchargedInterest())
            .add(getOverdueInterest());
  }


  @SuppressWarnings("unused")
  @Transient
  public BigDecimal getCurrentDebt() {
    return getTotalOverdue()
        .add(getServiceFee())
        .add(getPrincipal())
        .add(getInterest())
        .add(getConsultingFee())
        .add(getRestructuringFee());
  }

  @SuppressWarnings("unused")
  @Transient
  public BigDecimal getCurrentDebtWithoutOverdue() {
    return getServiceFee()
            .add(getPrincipal())
            .add(getInterest())
            .add(getConsultingFee())
            .add(getRestructuringFee());
  }
}
