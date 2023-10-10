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
    "defendant_name",
    "status_group",
    "suit_id",
    "court",
    "old_ic",
    "new_ic",
    "case_settled",
    "case_withdrawn",
    "defendant_address",
    "case_no",
    "plaintiff_details",
    "suit_ref",
    "hearing_date",
    "pending_status_date"
})
public class LegalSuitByRegno {

  @JsonProperty("defendant_name")
  private String defendantName;
  @JsonProperty("status_group")
  private String statusGroup;
  @JsonProperty("suit_id")
  private String suitId;
  @JsonProperty("court")
  private String court;
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
  @JsonProperty("case_no")
  private String caseNo;
  @JsonProperty("plaintiff_details")
  private List<PlaintiffDetail> plaintiffDetails;
  @JsonProperty("suit_ref")
  private String suitRef;
  @JsonProperty("hearing_date")
  private String hearingDate;
  @JsonProperty("pending_status_date")
  private String pendingStatusDate;
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
