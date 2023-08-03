package asia.atmonline.myriskservice.web.seon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class FraudResponse {

  private Boolean success;
  private SeonResponseError error;
  private FraudData data;

  public FraudResponse(SeonResponseError error) {
    this.success = false;
    this.error = error;
  }
}
