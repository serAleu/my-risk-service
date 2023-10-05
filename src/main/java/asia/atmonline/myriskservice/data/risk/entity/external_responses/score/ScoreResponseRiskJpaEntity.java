package asia.atmonline.myriskservice.data.risk.entity.external_responses.score;

import asia.atmonline.myriskservice.data.risk.entity.BaseRiskJpaEntity;
import com.fasterxml.jackson.annotation.JsonRootName;
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
@JsonRootName("scoring")
public class ScoreResponseRiskJpaEntity extends BaseRiskJpaEntity {

  @Column(name = "credit_application_id", nullable = false)
  private Long application_id;
  @Column(name = "score_node_id", nullable = false)
  private Integer node_id;
  @Column(name = "decision", nullable = false)
  private Integer decision;
  @Column(name = "score_limit")
  private Long scoreLimit;
  @Column(name = "term")
  private Long term;
  @Column(name = "grade")
  private String grade;
  @Column(name = "scoring")
  private Integer score;
  @Column(name = "probability")
  private Double probability;
  @Column(name = "model_id", nullable = false)
  private String model_id;
  @Column(name = "model_version", nullable = false)
  private String model_version;
  @Column(name = "status")
  private Integer status;
//  @Column(name = "predictors")
//  private String predictors;

  @Override
  public String repositoryName() {
    return "scoreResponseJpaRepository";
  }
}
