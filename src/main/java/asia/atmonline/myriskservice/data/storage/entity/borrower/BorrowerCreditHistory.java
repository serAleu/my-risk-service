package asia.atmonline.myriskservice.data.storage.entity.borrower;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "borrower_credit_history")
public class BorrowerCreditHistory extends BaseStorageEntity {

  @Column(name = "bank_id")
  private String bankId;

  @Column(name = "finance_type")
  private String financeType;

  @Column(name = "open_date")
  private LocalDate openDate;

  @Column(name = "account_status")
  private String accountStatus;

  @Column(name = "currency")
  private String currency;

  @Column(name = "loan_amount_credit_limit")
  private String loanAmountCreditLimit;

  @Column(name = "worst_payment_status")
  private String worstPaymentStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "borrower_id", nullable = false)
  private Borrower borrower;
}
