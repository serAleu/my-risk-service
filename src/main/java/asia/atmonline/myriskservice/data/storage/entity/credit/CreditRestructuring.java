package asia.atmonline.myriskservice.data.storage.entity.credit;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "credit_restructuring", schema = "my-back")
@SequenceGenerator(name = "sequence-generator", sequenceName = "credit_restructuring_id_seq", allocationSize = 1)
@NoArgsConstructor
@Getter
@Setter
public class CreditRestructuring extends BaseStorageEntity {

  @Column(name = "credit_id", nullable = false, updatable = false)
  private Long creditId;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "term", nullable = false)
//  private RestructuringTerm term;

  @Column(name = "fee", precision = 19, nullable = false)
  private BigDecimal fee;

  @Column(name = "otp_code", nullable = false)
  private String otpCode;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "status", nullable = false)
//  private RestructuringStatus status = RestructuringStatus.PENDING;

  @Column(name = "expired_at", nullable = false)
  private LocalDateTime expiredAt;

  @Column(name = "activated_at", nullable = false)
  private LocalDateTime activatedAt;

  @Column(name = "activation_delta")
  private Integer activationDelta;

  @Column(name = "created_by", nullable = false)
  private Long createdBy;

  @Column(name = "agreement_document_id", nullable = false)
  private Long agreementDocumentId;

  @Version
  @Column(name = "version", nullable = false)
  private Integer version;

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
    this.expiredAt = calculateExpireDate(createdAt);
  }

  private LocalDateTime calculateExpireDate(LocalDateTime createdAt) {
    if (createdAt != null) {
      return createdAt.plus(2, ChronoUnit.DAYS).withHour(23).withMinute(59).withSecond(59);
    }
    return null;
  }

}
