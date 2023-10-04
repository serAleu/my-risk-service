package asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "plaintiff_name",
    "suit_id",
    "case_settled",
    "case_withdrawn",
    "court",
    "old_ic",
    "new_ic",
    "plaintiff_address",
    "case_no",
    "defendant_details",
    "suit_ref",
    "hearing_date",
    "pending_status_date"
})
public class _0 {

  @JsonProperty("plaintiff_name")
  private String plaintiffName;
  @JsonProperty("suit_id")
  private String suitId;
  @JsonProperty("case_settled")
  private String caseSettled;
  @JsonProperty("case_withdrawn")
  private String caseWithdrawn;
  @JsonProperty("court")
  private String court;
  @JsonProperty("old_ic")
  private String oldIc;
  @JsonProperty("new_ic")
  private String newIc;
  @JsonProperty("plaintiff_address")
  private String plaintiffAddress;
  @JsonProperty("case_no")
  private String caseNo;
  @JsonProperty("defendant_details")
  private List<DefendantDetail> defendantDetails;
  @JsonProperty("suit_ref")
  private String suitRef;
  @JsonProperty("hearing_date")
  private String hearingDate;
  @JsonProperty("pending_status_date")
  private String pendingStatusDate;
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
