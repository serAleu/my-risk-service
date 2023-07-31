package asia.atmonline.myriskservice.messages.request;

import asia.atmonline.myriskservice.enums.GroupOfChecks;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseRequest {

  @JsonProperty("check_type")
  private GroupOfChecks check;
  @JsonProperty("application_id")
  private Long applicationId;

  @Override
  @Transient
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
