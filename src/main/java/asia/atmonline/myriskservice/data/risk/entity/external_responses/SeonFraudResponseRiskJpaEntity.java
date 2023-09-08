package asia.atmonline.myriskservice.data.risk.entity.external_responses;

import asia.atmonline.myriskservice.data.risk.entity.BaseRiskJpaEntity;
import asia.atmonline.myriskservice.web.seon.dto.FraudResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seon_fraud_response")
@SequenceGenerator(name = "sequence-generator", sequenceName = "seon_fraud_response_id_seq", allocationSize = 1)
public class SeonFraudResponseRiskJpaEntity extends BaseRiskJpaEntity {

  @Column(name = "credit_application_id", nullable = false)
  private Long creditApplicationId;
  @Column(name = "borrower_id", nullable = false)
  private Long borrowerId;
  @Column(name = "phone", nullable = false)
  private String phone;
  @Column(name = "response", nullable = false)
  @JdbcTypeCode(SqlTypes.JSON)
  private String response;
  @Column(name = "success")
  private Boolean success;
  @Column(name = "phone_request")
  private Boolean phoneRequest;
  @Column(name = "email_request")
  private Boolean emailRequest;
  @Column(name = "device_fingerprint_request")
  private Boolean deviceFingerprintRequest;
  @Column(name = "original_response")
  @JdbcTypeCode(SqlTypes.JSON)
  private String originalResponse;
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Transient
  private FraudResponse fraudResponse;

  @Override
  public String repositoryName() {
    return "seonFraudResponseJpaRepository";
  }
}
