package asia.atmonline.myriskservice.web.seon.dto;

import asia.atmonline.myriskservice.web.seon.dto.email.EmailData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FraudData {

  private String id;
  private String state;

  @JsonProperty("fraud_score")
  private BigDecimal fraudScore;

  private String version;

  @JsonProperty("calculation_time")
  private Integer calculationTime;

  @JsonProperty("seon_id")
  private String seonId;

  @JsonProperty("phone_details")
  private PhoneData phoneDetails;

  @JsonProperty("email_details")
  private EmailData emailDetails;

  @JsonProperty("device_details")
  private DeviceDetails deviceDetails;

  @JsonProperty("applied_rules")
  private List<AppliedRulesItem> appliedRules;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

}
