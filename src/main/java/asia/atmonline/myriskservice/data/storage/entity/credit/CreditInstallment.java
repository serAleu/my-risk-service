package asia.atmonline.myriskservice.data.storage.entity.credit;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credit_installment")
@Getter
@Setter
@NoArgsConstructor
public class CreditInstallment extends BaseStorageEntity {

  @Column(name = "credit_id", nullable = false, updatable = false)
  private Long creditId;

  @Column(name = "period_start", nullable = false)
  private LocalDate periodStart;

  @Column(name = "period_end", nullable = false)
  private LocalDate periodEnd;

  @Column(name = "real_period_start", nullable = false)
  private LocalDate realPeriodStart;

  @Column(name = "real_period_end", nullable = false)
  private LocalDate realPeriodEnd;

  @Column(name = "principal", precision = 19, scale = DEFAULT_AMOUNT_SCALE, nullable = false)
  private BigDecimal principal;

  @Column(name = "remaining_principal", precision = 19, scale = DEFAULT_AMOUNT_SCALE, nullable = false)
  private BigDecimal remainingPrincipal;

  @Column(name = "interest", precision = 19, scale = DEFAULT_AMOUNT_SCALE, nullable = false)
  private BigDecimal interest;

  @Column(name = "service_fee", precision = 19, nullable = false)
  private BigDecimal serviceFee;

  @Column(name = "processing_fee", precision = 19, nullable = false)
  private BigDecimal processingFee;

  @Column(name = "paid", nullable = false, columnDefinition = "bool default false")
  private boolean paid = false;

  @Column(name = "applied_restructuring")
  private Set<Long> appliedRestructuring = new HashSet<>();

  @Column(name = "period_num")
  private Integer periodNumber;

  @Embedded
  private EarlyRepaymentInstallmentData earlyRepaymentData;

  public CreditInstallment(LocalDate periodStart, LocalDate periodEnd, BigDecimal principal, BigDecimal interest, BigDecimal remainingPrincipal) {
    super();
    this.periodStart = periodStart;
    this.periodEnd = periodEnd;
    this.principal = principal;
    this.interest = interest;
    this.remainingPrincipal = remainingPrincipal;
  }

  public CreditInstallment(LocalDate periodStart, LocalDate periodEnd, BigDecimal principal,
      BigDecimal interest, BigDecimal remainingPrincipal, BigDecimal serviceFee,
      BigDecimal processingFee, Integer periodNumber, EarlyRepaymentInstallmentData earlyRepaymentData) {
    super();
    this.periodStart = periodStart;
    this.periodEnd = periodEnd;
    this.principal = principal;
    this.interest = interest;
    this.remainingPrincipal = remainingPrincipal;
    this.serviceFee = serviceFee;
    this.processingFee = processingFee;
    this.periodNumber = periodNumber;
    this.earlyRepaymentData = earlyRepaymentData;
  }

  public CreditInstallment(
          LocalDate realPeriodStart,
          LocalDate realPeriodEnd,
          LocalDate periodStart,
          LocalDate periodEnd,
          BigDecimal principal,
          BigDecimal interest,
          BigDecimal remainingPrincipal,
          BigDecimal serviceFee,
          BigDecimal processingFee,
          Integer periodNumber,
          EarlyRepaymentInstallmentData earlyRepaymentData) {
    super();
    this.realPeriodStart = realPeriodStart;
    this.realPeriodEnd = realPeriodEnd;
    this.periodStart = periodStart;
    this.periodEnd = periodEnd;
    this.principal = principal;
    this.interest = interest;
    this.remainingPrincipal = remainingPrincipal;
    this.serviceFee = serviceFee;
    this.processingFee = processingFee;
    this.periodNumber = periodNumber;
    this.earlyRepaymentData = earlyRepaymentData;
  }

  public void successfullyPaid() {
    this.paid = true;
  }

  public void unpaid() {
    this.paid = false;
  }

  public BigDecimal getMonthlyPayment(boolean needApplyGracePeriodRule) {
    return getMonthlyPaymentInner(needApplyGracePeriodRule);
  }

  @Transient
  public BigDecimal getMonthlyPayment() {
    return getMonthlyPaymentInner(false);
  }

  private BigDecimal getMonthlyPaymentInner(boolean needApplyGracePeriodRule) {
    BigDecimal monthlyPayment = BigDecimal.ZERO;
    try {
      monthlyPayment = monthlyPayment.add(getPrincipal() != null ? getPrincipal() : BigDecimal.ZERO);
      if(!needApplyGracePeriodRule) {
        monthlyPayment = monthlyPayment.add(getInterest() != null ? getInterest() : BigDecimal.ZERO)
                .add(getServiceFee() != null ? getServiceFee() : BigDecimal.ZERO)
                .add(getProcessingFee() != null ? getProcessingFee() : BigDecimal.ZERO);
      }
    } catch (Exception e) {
//      log.error("exception during calculation of monthly payment", e);
      monthlyPayment = BigDecimal.ZERO;
    }

    return monthlyPayment;
  }

  public LocalDate getRealPeriodStart() {
    return this.realPeriodStart != null ? this.realPeriodStart : this.periodStart;
  }

  public LocalDate getRealPeriodEnd() {
    return this.realPeriodEnd != null ? this.realPeriodEnd : this.periodEnd;
  }


  @Transient
  public BigDecimal getAmount() {
    return getPrincipal().add(getInterest());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    CreditInstallment that = (CreditInstallment) o;
    return Objects.equals(periodStart, that.periodStart) &&
        Objects.equals(periodEnd, that.periodEnd) &&
        Objects.equals(principal, that.principal) &&
        Objects.equals(remainingPrincipal, that.remainingPrincipal) &&
        Objects.equals(interest, that.interest) &&
        Objects.equals(serviceFee, that.serviceFee) &&
        Objects.equals(processingFee, that.processingFee);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), periodStart, periodEnd, principal, remainingPrincipal, interest, serviceFee, processingFee);
  }
}
