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
    "earlier_aprv_facility",
    "latest_aprv_facility",
    "secured_facility",
    "unsecured_facility",
    "average_utilize_months",
    "utilization_12months_chargecard",
    "no_of_facility_education_loan",
    "no_of_local_lenders",
    "no_of_foreign_lenders"
})
public class CcrisKeyStatistics {

  @JsonProperty("earlier_aprv_facility")
  private List<EarlierAprvFacility> earlierAprvFacility;
  @JsonProperty("latest_aprv_facility")
  private LatestAprvFacility latestAprvFacility;
  @JsonProperty("secured_facility")
  private SecuredFacility securedFacility;
  @JsonProperty("unsecured_facility")
  private UnsecuredFacility unsecuredFacility;
  @JsonProperty("average_utilize_months")
  private AverageUtilizeMonths averageUtilizeMonths;
  @JsonProperty("utilization_12months_chargecard")
  private Utilization12monthsChargecard utilization12monthsChargecard;
  @JsonProperty("no_of_facility_education_loan")
  private Integer noOfFacilityEducationLoan;
  @JsonProperty("no_of_local_lenders")
  private Integer noOfLocalLenders;
  @JsonProperty("no_of_foreign_lenders")
  private Integer noOfForeignLenders;
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
