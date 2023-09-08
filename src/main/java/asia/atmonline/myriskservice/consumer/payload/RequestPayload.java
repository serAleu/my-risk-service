package asia.atmonline.myriskservice.consumer.payload;

import asia.atmonline.myriskservice.enums.risk.CheckType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@Getter
@Setter
public class RequestPayload {

  @JsonProperty("score_node_id")
  private String scoreNodeId;
  @JsonProperty("check_type")
  private CheckType checkType;
  @JsonProperty("application_id")
  private Long applicationId;
  @JsonProperty("phone_num")
  private String phone;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
