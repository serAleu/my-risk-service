package asia.atmonline.myriskservice.data.entity.risk.requests;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.enums.risk.CheckType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "risk_request")
@SequenceGenerator(name = "sequence-generator", sequenceName = "risk_request_id_seq", allocationSize = 1)
public class RiskRequestJpaEntity extends BaseJpaEntity {

  @JsonProperty("check_type")
  @Enumerated(EnumType.STRING)
  @Column(name = "check_type", nullable = false)
  private CheckType checkType;

  @JsonProperty("application_id")
  @Column(name = "credit_application_id")
  private Long creditApplicationId;

  @JsonProperty("phone_num")
  @Column(name = "phone_num")
  private String phoneNum;

  @JsonProperty("score_node_id")
  @Column(name = "score_node_id")
  private Long scoreNodeId;

  @Override
  @Transient
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

  @Override
  public String repositoryName() {
    return "riskRequestJpaRepository";
  }
}