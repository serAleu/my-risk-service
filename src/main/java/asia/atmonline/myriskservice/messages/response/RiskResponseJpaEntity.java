package asia.atmonline.myriskservice.messages.response;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.enums.risk.FinalDecision;
import asia.atmonline.myriskservice.enums.risk.GroupOfChecks;
import asia.atmonline.myriskservice.enums.risk.RejectionReasonCode;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import java.util.HashMap;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString.Exclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class RiskResponseJpaEntity<P extends BaseSqsProducer> extends BaseJpaEntity {

  @JsonProperty("final_decision")
  @Enumerated(EnumType.STRING)
  private FinalDecision decision;
  @JsonProperty("rejection_reason")
  @Enumerated(EnumType.STRING)
  @Nullable
  private RejectionReasonCode rejectionReasonCode;
  @JsonProperty("check_type")
  @Enumerated(EnumType.STRING)
  private GroupOfChecks check;
  @JsonProperty("application_id")
  @Nullable
  private Long applicationId;
  @JsonProperty("phone_num")
  @Nullable
  private String phone_num;
  @Nullable
  private Map<String, String> additionalFields = new HashMap<>();

  @JsonIgnore
  @Exclude
  @Setter(AccessLevel.NONE)
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
