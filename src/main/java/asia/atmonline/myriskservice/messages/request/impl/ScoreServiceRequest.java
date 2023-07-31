package asia.atmonline.myriskservice.messages.request.impl;

import asia.atmonline.myriskservice.enums.ProductCode;
import asia.atmonline.myriskservice.messages.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ScoreServiceRequest extends BaseRequest {

  @JsonProperty("app_id")
  private Long appId;
  @JsonProperty("node_id")
  private Long nodeId;
  @JsonProperty("product")
  private ProductCode product;

}
