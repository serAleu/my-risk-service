package asia.atmonline.myriskservice.data.storage.entity.credit;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import asia.atmonline.myriskservice.enums.credit.PromisedPaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "promised_payment")
public class PromisedPayment extends BaseStorageEntity {

  @JoinColumn(name = "credit_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Credit credit;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private PromisedPaymentStatus status = PromisedPaymentStatus.OPEN;

  @Column(name = "promised_date")
  private LocalDate promisedDate;

  @Column(name = "amount", precision = 19, nullable = false)
  private BigDecimal amount;

  @Column(name = "comment")
  private String comment;

//  @Fetch(FetchMode.SELECT)
//  @ManyToOne(fetch = FetchType.EAGER)
//  @JoinColumn(name = "user_id")
//  private BackOfficeUserAccount user;

  public Credit getCredit() {
    return credit;
  }

  public void setCredit(Credit credit) {
    this.credit = credit;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public PromisedPaymentStatus getStatus() {
    return status;
  }

  public void setStatus(PromisedPaymentStatus status) {
    this.status = status;
  }

  public LocalDate getPromisedDate() {
    return promisedDate;
  }

  public void setPromisedDate(LocalDate promisedDate) {
    this.promisedDate = promisedDate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

//  public BackOfficeUserAccount getUser() {
//    return user;
//  }
//
//  public void setUser(BackOfficeUserAccount user) {
//    this.user = user;
//  }
}
