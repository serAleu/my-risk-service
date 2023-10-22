package asia.atmonline.myriskservice.web.seon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class SeonResponseError {

  private String message;
  private String code;

  public SeonResponseError(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
