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
    "no_of_facility",
    "total_outstanding_balance",
    "total_outstanding_against_ttllimit",
    "max_iia_last_12months"
})
public class UnsecuredFacility {

  @JsonProperty("no_of_facility")
  private Integer noOfFacility;
  @JsonProperty("total_outstanding_balance")
  private Integer totalOutstandingBalance;
  @JsonProperty("total_outstanding_against_ttllimit")
  private String totalOutstandingAgainstTtllimit;
  @JsonProperty("max_iia_last_12months")
  private String maxIiaLast12months;
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
