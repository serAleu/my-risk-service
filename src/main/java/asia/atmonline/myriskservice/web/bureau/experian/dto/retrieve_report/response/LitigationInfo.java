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
    "legal_suit_by_regno",
    "legal_suit_proclamation_by_regno",
    "others_known_legal_suit",
    "legal_suit_by_plaintiff",
    "person_bankruptcy"
})
public class LitigationInfo {

  @JsonProperty("legal_suit_by_regno")
  private List<LegalSuitByRegno> legalSuitByRegno;
  @JsonProperty("legal_suit_proclamation_by_regno")
  private LegalSuitProclamationByRegno legalSuitProclamationByRegno;
  @JsonProperty("others_known_legal_suit")
  private List<OthersKnownLegalSuit> othersKnownLegalSuit;
  @JsonProperty("legal_suit_by_plaintiff")
  private LegalSuitByPlaintiff legalSuitByPlaintiff;
  @JsonProperty("person_bankruptcy")
  private PersonBankruptcy personBankruptcy;
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
