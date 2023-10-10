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
    "i_score",
    "risk_grade",
    "key_factor",
    "grade_format",
    "legend_iscore_ccris_gen2",
    "i_score_risk_grade_format_consumer"
})
public class IScore {

  @JsonProperty("i_score")
  private String iScore;
  @JsonProperty("risk_grade")
  private String riskGrade;
  @JsonProperty("key_factor")
  private List<String> keyFactor;
  @JsonProperty("grade_format")
  private String gradeFormat;
  @JsonProperty("legend_iscore_ccris_gen2")
  private String legendIscoreCcrisGen2;
  @JsonProperty("i_score_risk_grade_format_consumer")
  private String iScoreRiskGradeFormatConsumer;
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
