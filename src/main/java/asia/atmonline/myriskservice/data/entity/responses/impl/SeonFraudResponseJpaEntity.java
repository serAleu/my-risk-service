package asia.atmonline.myriskservice.data.entity.responses.impl;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.web.seon.dto.FraudResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@RequiredArgsConstructor
@Entity
@Table(name = "seon_fraud_response")
@SequenceGenerator(name = "sequence-generator", sequenceName = "seon_fraud_response_id_seq", allocationSize = 1)
public class SeonFraudResponseJpaEntity extends BaseJpaEntity {

  @Column(name = "application_id", nullable = false)
  private Long applicationId;
  @Column(name = "borrower_id", nullable = false)
  private Long borrowerId;
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;
  @Column(name = "phone", nullable = false)
  private String phone;
  @Column(name = "response", nullable = false)
  private FraudResponse response;
  @Column(name = "success")
  private Boolean success;
  @Column(name = "phone_request")
  private Boolean phoneRequest;
  @Column(name = "email_request")
  private Boolean emailRequest;
  @Column(name = "device_fingerprint_request")
  private Boolean deviceFingerprintRequest;
  @Column(name = "original_response")
  private String originalResponse;

  public SeonFraudResponseJpaEntity(Long applicationId, Long borrowerId, String phone,
      FraudResponse response, Boolean success) {
    this.applicationId = applicationId;
    this.borrowerId = borrowerId;
    this.phone = phone;
    this.response = response;
    this.createdAt = LocalDateTime.now();
    this.success = success;
  }

  @Override
  public String repositoryName() {
    return "seonFraudResponseJpaRepository";
  }
}
