package asia.atmonline.myriskservice.data.entity.risk.requests.impl;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.enums.application.ProductCode;
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
@Table(name = "score_checks_request")
@SequenceGenerator(name = "sequence-generator", sequenceName = "score_checks_request_id_seq", allocationSize = 1)
public class ScoreRequestJpaEntity extends BaseJpaEntity {

  @Column(name = "score_node_id", nullable = false)
  private Long scoreNodeId;
  @Column(name = "product", nullable = false)
  private ProductCode product;
  @Column(name = "credit_application_id", nullable = false)
  private Long creditApplicationId;

  @Override
  public String repositoryName() {
    return "scoreRequestJpaRepository";
  }
}
