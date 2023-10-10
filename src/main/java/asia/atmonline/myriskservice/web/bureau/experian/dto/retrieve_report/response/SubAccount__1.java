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
    "status",
    "restructure_reschedule_date",
    "facility",
    "total_outstanding_balance",
    "total_outstanding_balance_bnm",
    "balance_updated_date",
    "installment_amount",
    "principle_repayment_term",
    "collateral_type",
    "credit_position",
    "collateral_type_code"
})
public class SubAccount__1 {

  @JsonProperty("status")
  private String status;
  @JsonProperty("restructure_reschedule_date")
  private String restructureRescheduleDate;
  @JsonProperty("facility")
  private String facility;
  @JsonProperty("total_outstanding_balance")
  private String totalOutstandingBalance;
  @JsonProperty("total_outstanding_balance_bnm")
  private String totalOutstandingBalanceBnm;
  @JsonProperty("balance_updated_date")
  private String balanceUpdatedDate;
  @JsonProperty("installment_amount")
  private String installmentAmount;
  @JsonProperty("principle_repayment_term")
  private String principleRepaymentTerm;
  @JsonProperty("collateral_type")
  private String collateralType;
  @JsonProperty("credit_position")
  private List<String> creditPosition;
  @JsonProperty("collateral_type_code")
  private String collateralTypeCode;
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
