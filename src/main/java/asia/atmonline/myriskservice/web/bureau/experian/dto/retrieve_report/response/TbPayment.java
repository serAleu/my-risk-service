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
    "payment_profile",
    "payment_trend",
    "p2p_fintech",
    "show_note",
    "show_note_fintect"
})
public class TbPayment {

  @JsonProperty("payment_profile")
  private List<Object> paymentProfile;
  @JsonProperty("payment_trend")
  private PaymentTrend paymentTrend;
  @JsonProperty("p2p_fintech")
  private P2pFintech p2pFintech;
  @JsonProperty("show_note")
  private String showNote;
  @JsonProperty("show_note_fintect")
  private String showNoteFintect;
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
