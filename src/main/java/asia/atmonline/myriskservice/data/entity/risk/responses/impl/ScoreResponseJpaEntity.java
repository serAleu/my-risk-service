package asia.atmonline.myriskservice.data.entity.risk.responses.impl;

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
@Table(name = "score_checks_response")
@SequenceGenerator(name = "sequence-generator", sequenceName = "score_checks_response_id_seq", allocationSize = 1)
public class ScoreResponseJpaEntity extends BaseJpaEntity {

  @Column(name = "credit_application_id", nullable = false)
  private Long creditApplicationId;
  @Column(name = "score_node_id", nullable = false)
  private Integer scoreNodeId;
  @Column(name = "decision", nullable = false)
  private Integer decision;
  @Column(name = "limit")
  private Long limit;
  @Column(name = "term")
  private Integer term;
  @Column(name = "grade")
  private String grade;
  @Column(name = "score")
  private Integer score;
  @Column(name = "probability")
  private Double probability;
  @Column(name = "model_id", nullable = false)
  private String modelId;
  @Column(name = "model_version", nullable = false)
  private String modelVersion;
  @Column(name = "status")
  private Integer status;
  @Column(name = "predictors")
  private String predictors;

  @Override
  public String repositoryName() {
    return "scoreResponseJpaRepository";
  }
}
