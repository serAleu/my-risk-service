package asia.atmonline.myriskservice.data.entity.risk.responses;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.enums.risk.FinalDecision;
import asia.atmonline.myriskservice.enums.risk.GroupOfChecks;
import asia.atmonline.myriskservice.enums.risk.RejectionReasonCode;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@Entity
@Table(name = "risk_response")
@SequenceGenerator(name = "sequence-generator", sequenceName = "risk_response_id_seq", allocationSize = 1)
public class RiskResponseJpaEntity<P extends BaseSqsProducer> extends BaseJpaEntity {

  @JsonProperty("final_decision")
  @Enumerated(EnumType.STRING)
  @Column(name = "decision", nullable = false)
  private FinalDecision decision;

  @JsonProperty("rejection_reason")
  @Enumerated(EnumType.STRING)
  @Column(name = "rejection_reason_code")
  private RejectionReasonCode rejectionReasonCode;

  @JsonProperty("check_type")
  @Enumerated(EnumType.STRING)
  @Column(name = "check", nullable = false)
  private GroupOfChecks check;

  @JsonProperty("credit_application_id")
  @Column(name = "credit_application_id")
  private Long creditApplicationId;

  @JsonProperty("borrower_id")
  @Column(name = "borrower_id")
  private Long borrowerId;

  @JsonProperty("phone_num")
  @Column(name = "phone_num")
  private String phone_num;

  @JsonProperty("additional_fields")
  @Column(name = "additional_fields")
  @ElementCollection
  private Map<String, String> additionalFields = new HashMap<>();

  @JsonIgnore
  @Column(name = "request_id")
  private Long requestId;

  @JsonIgnore
  @Setter(AccessLevel.NONE)
  @Transient
  private P producer;

  public P getProducer() {
    return producer;
  }

  @JsonAnySetter
  public void setAdditionalField(String fieldName, String fieldValue) {
    if(additionalFields == null) {
      additionalFields = new HashMap<>();
    }
    additionalFields.put(fieldName, fieldValue);
  }

  @Override
  @Transient
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

  @Override
  public String repositoryName() {
    return "riskResponseJpaRepository";
  }
}
