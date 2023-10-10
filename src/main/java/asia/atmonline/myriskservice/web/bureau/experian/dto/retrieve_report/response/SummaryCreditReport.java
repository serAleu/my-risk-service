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
    "approved_count",
    "approved_amount",
    "pending_count",
    "pending_amount"
})
public class SummaryCreditReport {

  @JsonProperty("approved_count")
  private String approvedCount;
  @JsonProperty("approved_amount")
  private String approvedAmount;
  @JsonProperty("pending_count")
  private String pendingCount;
  @JsonProperty("pending_amount")
  private String pendingAmount;
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
