package asia.atmonline.myriskservice.messages.response;

import asia.atmonline.myriskservice.enums.FinalDecision;
import asia.atmonline.myriskservice.enums.GroupOfChecks;
import asia.atmonline.myriskservice.enums.RejectionReasonCode;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString.Exclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
public class RiskResponse<P extends BaseSqsProducer> {

  @JsonProperty("final_decision")
  private FinalDecision decision;
  @JsonProperty("rejection_reason")
  private RejectionReasonCode rejectionReasonCode;
  @JsonProperty("check_type")
  private GroupOfChecks check;
  @JsonProperty("application_id")
  private Long applicationId;
  @JsonProperty("phone_num")
  private String phone_num;

  @JsonIgnore
  @Exclude
  @Setter(AccessLevel.NONE)
  private P producer;

  public P getProducer() {
    return producer;
  }

  @Override
  @Transient
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
