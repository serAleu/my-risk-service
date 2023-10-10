package asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.response;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "master_id",
    "date",
    "capacity",
    "lender_type",
    "limit",
    "legal_status",
    "legal_status_date",
    "collateral_type",
    "financial_group_resident_status",
    "collateral_type_code"
})
@Generated("jsonschema2pojo")
public class Master__1 {

  @JsonProperty("master_id")
  private String masterId;
  @JsonProperty("date")
  private String date;
  @JsonProperty("capacity")
  private String capacity;
  @JsonProperty("lender_type")
  private String lenderType;
  @JsonProperty("limit")
  private String limit;
  @JsonProperty("legal_status")
  private String legalStatus;
  @JsonProperty("legal_status_date")
  private String legalStatusDate;
  @JsonProperty("collateral_type")
  private String collateralType;
  @JsonProperty("financial_group_resident_status")
  private String financialGroupResidentStatus;
  @JsonProperty("collateral_type_code")
  private String collateralTypeCode;
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
