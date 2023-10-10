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
    "defendant_name",
    "status_group",
    "suit_id",
    "court",
    "foreclosure_case",
    "old_ic",
    "new_ic",
    "case_settled",
    "case_withdrawn",
    "defendant_address",
    "proclamation_of_sale",
    "suit_ref",
    "summon_date"
})
public class HighCourt {

  @JsonProperty("defendant_name")
  private String defendantName;
  @JsonProperty("status_group")
  private String statusGroup;
  @JsonProperty("suit_id")
  private String suitId;
  @JsonProperty("court")
  private String court;
  @JsonProperty("foreclosure_case")
  private Boolean foreclosureCase;
  @JsonProperty("old_ic")
  private String oldIc;
  @JsonProperty("new_ic")
  private String newIc;
  @JsonProperty("case_settled")
  private String caseSettled;
  @JsonProperty("case_withdrawn")
  private String caseWithdrawn;
  @JsonProperty("defendant_address")
  private String defendantAddress;
  @JsonProperty("proclamation_of_sale")
  private String proclamationOfSale;
  @JsonProperty("suit_ref")
  private String suitRef;
  @JsonProperty("summon_date")
  private String summonDate;
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
