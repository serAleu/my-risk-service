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
    "start_year",
    "end_year",
    "month",
    "outstanding_credit",
    "credit_application",
    "special_attention_account",
    "facilities_remark",
    "status_remark",
    "legal_remark"
})
public class CcrisBankingDetails {

  @JsonProperty("start_year")
  private String startYear;
  @JsonProperty("end_year")
  private Integer endYear;
  @JsonProperty("month")
  private List<String> month;
  @JsonProperty("outstanding_credit")
  private List<OutstandingCredit> outstandingCredit;
  @JsonProperty("credit_application")
  private List<Object> creditApplication;
  @JsonProperty("special_attention_account")
  private List<SpecialAttentionAccount> specialAttentionAccount;
  @JsonProperty("facilities_remark")
  private List<FacilitiesRemark> facilitiesRemark;
  @JsonProperty("status_remark")
  private List<StatusRemark> statusRemark;
  @JsonProperty("legal_remark")
  private List<LegalRemark> legalRemark;
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
