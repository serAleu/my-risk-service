package asia.atmonline.myriskservice.consumer.payload;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@Getter
@Setter
public class BlackListRequestPayload {


  private Long applicationId;
  private Long borrowerId;
  private Long userId;
  private String rejectCode;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
