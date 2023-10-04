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
    "status_group",
    "defendant_name",
    "old_ic",
    "new_ic",
    "defendant_address",
    "case_no",
    "receiving_date",
    "case_settled",
    "case_withdrawn"
})
public class Case {

  @JsonProperty("status_group")
  private String statusGroup;
  @JsonProperty("defendant_name")
  private String defendantName;
  @JsonProperty("old_ic")
  private String oldIc;
  @JsonProperty("new_ic")
  private String newIc;
  @JsonProperty("defendant_address")
  private String defendantAddress;
  @JsonProperty("case_no")
  private String caseNo;
  @JsonProperty("receiving_date")
  private String receivingDate;
  @JsonProperty("case_settled")
  private String caseSettled;
  @JsonProperty("case_withdrawn")
  private String caseWithdrawn;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }

}
