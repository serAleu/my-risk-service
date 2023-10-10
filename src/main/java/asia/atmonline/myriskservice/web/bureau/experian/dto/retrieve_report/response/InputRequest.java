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
    "search_name",
    "old_ic",
    "new_ic",
    "product_code",
    "nationality",
    "subscriber_refno"
})
public class InputRequest {

  @JsonProperty("search_name")
  private String searchName;
  @JsonProperty("old_ic")
  private String oldIc;
  @JsonProperty("new_ic")
  private String newIc;
  @JsonProperty("product_code")
  private String productCode;
  @JsonProperty("nationality")
  private String nationality;
  @JsonProperty("subscriber_refno")
  private String subscriberRefno;
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
