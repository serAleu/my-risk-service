package asia.atmonline.myriskservice.data.risk.entity;

import asia.atmonline.myriskservice.enums.risk.CheckType;
import asia.atmonline.myriskservice.enums.risk.FinalDecision;
import asia.atmonline.myriskservice.enums.risk.RejectionReasonCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "risk_response")
@SequenceGenerator(name = "sequence-generator", sequenceName = "risk_response_id_seq", allocationSize = 1)
public class RiskResponseJpaEntity extends BaseRiskJpaEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "final_decision", nullable = false)
  private FinalDecision decision;

  @Enumerated(EnumType.STRING)
  @Column(name = "rejection_reason_code")
  private RejectionReasonCode rejectionReason;

  @Enumerated(EnumType.STRING)
  @Column(name = "check_type", nullable = false)
  private CheckType checkType;

  @Column(name = "credit_application_id")
  private Long applicationId;

  @Column(name = "borrower_id")
  private Long borrowerId;

  @Column(name = "phone_num")
  private String phone;

  @Column(name = "risk_request_id")
  private Long requestId;
//
//  @JsonProperty("additional_fields")
//  @Column(name = "additional_fields")
//  @ElementCollection
//  private Map<String, String> additionalFields = new HashMap<>();



//  @JsonAnySetter
//  public void setAdditionalField(String fieldName, String fieldValue) {
//    if(additionalFields == null) {
//      additionalFields = new HashMap<>();
//    }
//    additionalFields.put(fieldName, fieldValue);
//  }

  @Override
  public String repositoryName() {
    return "riskResponseJpaRepository";
  }
}
