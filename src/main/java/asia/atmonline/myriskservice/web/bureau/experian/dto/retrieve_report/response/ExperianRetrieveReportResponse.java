package asia.atmonline.myriskservice.web.bureau.experian.dto.retrieve_report.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "report_date",
    "summary",
    "banking_info",
    "litigation_info",
    "trade_bureau",
    "aml_sanction",
    "enquiry",
    "legend_gen2",
    "end"
})
public class ExperianRetrieveReportResponse {

  @JsonProperty("report_date")
  private String reportDate;
  @JsonProperty("summary")
  private Summary summary;
  @JsonProperty("banking_info")
  private BankingInfo bankingInfo;
  @JsonProperty("litigation_info")
  private LitigationInfo litigationInfo;
  @JsonProperty("trade_bureau")
  private TradeBureau tradeBureau;
  @JsonProperty("aml_sanction")
  private AmlSanction amlSanction;
  @JsonProperty("enquiry")
  private Enquiry enquiry;
  @JsonProperty("legend_gen2")
  private LegendGen2 legendGen2;
  @JsonProperty("end")
  private End end;
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

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
