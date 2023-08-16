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
@Table(name = "bureau_checks_request")
@SequenceGenerator(name = "sequence-generator", sequenceName = "bureau_checks_request_id_seq", allocationSize = 1)
public class BureauRequestJpaEntity extends BaseJpaEntity {

  @Column(name = "borrower_id", nullable = false)
  private Long borrowerId;
  @Column(name = "credit_application_id", nullable = false)
  private Long creditApplicationId;

  @Override
  public String repositoryName() {
    return "bureauRequestJpaRepository";
  }
}
