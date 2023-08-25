package asia.atmonline.myriskservice.consumer.payload;

import asia.atmonline.myriskservice.enums.risk.CheckType;
import asia.atmonline.myriskservice.enums.risk.FinalDecision;
import asia.atmonline.myriskservice.enums.risk.RejectionReasonCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@Builder
public class ResponsePayload {

  private FinalDecision decision;
  private RejectionReasonCode rejectionReason;
  private CheckType checkType;
  private Long applicationId;
  private String phone;
  private Long borrowerId;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
