package asia.atmonline.myriskservice.web.bureau.experian.dto.confirm_entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@AllArgsConstructor
public class ExperianCCRISEntityRequest {

  private ExperianCCRISEntityRequestBody request;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
