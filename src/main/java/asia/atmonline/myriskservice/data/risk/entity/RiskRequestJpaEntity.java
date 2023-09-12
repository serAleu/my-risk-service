package asia.atmonline.myriskservice.data.risk.entity;

import asia.atmonline.myriskservice.enums.risk.CheckType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "risk_request")
@SequenceGenerator(name = "sequence-generator", sequenceName = "risk_request_id_seq", allocationSize = 1)
public class RiskRequestJpaEntity extends BaseRiskJpaEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "check_type", nullable = false)
  private CheckType checkType;

  @Column(name = "credit_application_id")
  private Long applicationId;

  @Column(name = "phone_num")
  private String phone;

  @Column(name = "score_node_id")
  private Long scoreNodeId;

  @Override
  public String repositoryName() {
    return "riskRequestJpaRepository";
  }
}