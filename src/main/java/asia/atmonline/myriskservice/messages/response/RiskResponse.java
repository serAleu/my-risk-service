package asia.atmonline.myriskservice.messages.response;

import asia.atmonline.myriskservice.enums.FinalDecision;
import asia.atmonline.myriskservice.enums.GroupOfChecks;
import asia.atmonline.myriskservice.enums.RejectionReasonCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@Accessors(chain = true)
public class RiskResponse {

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

  @Override
  @Transient
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
