package asia.atmonline.myriskservice.consumer.payload;

import asia.atmonline.myriskservice.enums.risk.CheckType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RequestPayload {

  private String scoreNodeId;
  private CheckType checkType;
  private Long applicationId;
  private String phone;
}
