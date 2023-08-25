package asia.atmonline.myriskservice.consumer.payload;

import asia.atmonline.myriskservice.enums.risk.CheckType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@Getter
@Setter
public class RequestPayload {

  private String scoreNodeId;
  private CheckType checkType;
  private Long applicationId;
  private String phone;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
