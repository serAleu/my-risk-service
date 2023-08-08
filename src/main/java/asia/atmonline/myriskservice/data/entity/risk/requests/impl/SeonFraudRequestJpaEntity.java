package asia.atmonline.myriskservice.data.entity.risk.requests.impl;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@Entity
@Table(name = "seon_fraud_request")
@SequenceGenerator(name = "sequence-generator", sequenceName = "seon_fraud_request_id_seq", allocationSize = 1)
public class SeonFraudRequestJpaEntity extends BaseJpaEntity {

  @Column(name = "application_id", nullable = false)
  private Long applicationId;
  @Column(name = "borrower_id", nullable = false)
  private Long borrowerId;
  @Column(name = "original_request")
  private String originalRequest;

  @Override
  public String repositoryName() {
    return "seonFraudRequestJpaRepository";
  }
}
