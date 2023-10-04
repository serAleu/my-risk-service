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
    "input_request",
    "ccris_individual_info",
    "ccris_individual_addresses",
    "info_summary",
    "i_score",
    "person_company_interests",
    "previous_company_interests"
})
public class Summary {

  @JsonProperty("input_request")
  private InputRequest inputRequest;
  @JsonProperty("ccris_individual_info")
  private CcrisIndividualInfo ccrisIndividualInfo;
  @JsonProperty("ccris_individual_addresses")
  private List<Object> ccrisIndividualAddresses;
  @JsonProperty("info_summary")
  private InfoSummary infoSummary;
  @JsonProperty("i_score")
  private IScore iScore;
  @JsonProperty("person_company_interests")
  private List<Object> personCompanyInterests;
  @JsonProperty("previous_company_interests")
  private List<Object> previousCompanyInterests;
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
