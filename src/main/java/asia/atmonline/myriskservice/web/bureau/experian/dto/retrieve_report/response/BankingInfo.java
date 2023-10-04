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
    "ccris_selected_by_you",
    "ccris_banking_warning",
    "ccris_key_statistics",
    "ccris_banking_summary",
    "ccris_banking_details"
})
public class BankingInfo {

  @JsonProperty("ccris_selected_by_you")
  private CcrisSelectedByYou ccrisSelectedByYou;
  @JsonProperty("ccris_banking_warning")
  private String ccrisBankingWarning;
  @JsonProperty("ccris_key_statistics")
  private CcrisKeyStatistics ccrisKeyStatistics;
  @JsonProperty("ccris_banking_summary")
  private CcrisBankingSummary ccrisBankingSummary;
  @JsonProperty("ccris_banking_details")
  private CcrisBankingDetails ccrisBankingDetails;
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
