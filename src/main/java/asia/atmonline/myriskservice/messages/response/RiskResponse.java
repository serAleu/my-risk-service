package asia.atmonline.myriskservice.messages.response;

import jakarta.persistence.Transient;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RiskResponse {

  @Override
  @Transient
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
