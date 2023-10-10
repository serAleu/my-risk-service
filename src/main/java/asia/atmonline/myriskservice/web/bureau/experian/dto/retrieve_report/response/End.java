package asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "subscriber_name",
    "username",
    "order_date",
    "order_time",
    "userid",
    "order_id"
})
public class End {

  @JsonProperty("subscriber_name")
  private String subscriberName;
  @JsonProperty("username")
  private String username;
  @JsonProperty("order_date")
  private String orderDate;
  @JsonProperty("order_time")
  private String orderTime;
  @JsonProperty("userid")
  private String userid;
  @JsonProperty("order_id")
  private String orderId;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new LinkedHashMap<>();

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
