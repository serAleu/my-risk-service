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
    "credit_approval_count",
    "credit_pending_count",
    "special_attention_account_count",
    "legal_action_banking_count",
    "existing_facility_count",
    "bankruptcy_count",
    "legal_suit_count",
    "trade_bureau_count",
    "enquiry_count",
    "interest_count"
})
public class InfoSummary {

  @JsonProperty("credit_approval_count")
  private String creditApprovalCount;
  @JsonProperty("credit_pending_count")
  private String creditPendingCount;
  @JsonProperty("special_attention_account_count")
  private Integer specialAttentionAccountCount;
  @JsonProperty("legal_action_banking_count")
  private Integer legalActionBankingCount;
  @JsonProperty("existing_facility_count")
  private Integer existingFacilityCount;
  @JsonProperty("bankruptcy_count")
  private Integer bankruptcyCount;
  @JsonProperty("legal_suit_count")
  private Integer legalSuitCount;
  @JsonProperty("trade_bureau_count")
  private Integer tradeBureauCount;
  @JsonProperty("enquiry_count")
  private String enquiryCount;
  @JsonProperty("interest_count")
  private Integer interestCount;
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
