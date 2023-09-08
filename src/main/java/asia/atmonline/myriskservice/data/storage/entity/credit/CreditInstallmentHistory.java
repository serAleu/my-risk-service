package asia.atmonline.myriskservice.data.storage.entity.credit;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credit_installment_history", schema = "my-back")
@SequenceGenerator(name = "sequence-generator", sequenceName = "credit_installment_history_id_seq", allocationSize = 1)
@NoArgsConstructor
@Getter
@Setter
public class CreditInstallmentHistory extends BaseStorageEntity {

  @Column(name = "version_num")
  private Integer versionNumber;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "version_type")
//  private CreditInstallmentHistoryType versionType;

  @Column(name = "version_date_from", nullable = false)
  private LocalDate versionDateFrom;

  @Column(name = "version_date_to", nullable = false)
  private LocalDate versionDateTo;

  @Column(name = "version_actual", nullable = false)
  private Boolean versionActual;

  @Column(name = "credit_id", nullable = false)
  private Long creditId;

  @Column(name = "period_start", nullable = false)
  private LocalDate periodStart;

  @Column(name = "period_end", nullable = false)
  private LocalDate periodEnd;

  @Column(name = "principal", precision = 19, nullable = false)
  private BigDecimal principal;

  @Column(name = "remaining_principal", precision = 19, nullable = false)
  private BigDecimal remainingPrincipal;

  @Column(name = "interest", precision = 19, nullable = false)
  private BigDecimal interest;

  @Column(name = "service_fee", precision = 19, nullable = false)
  private BigDecimal serviceFee;

  @Column(name = "processing_fee", precision = 19, nullable = false)
  private BigDecimal processingFee;

  @Column(name = "period_num")
  private Integer periodNumber;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

}
