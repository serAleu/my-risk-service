package asia.atmonline.myriskservice.web.seon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class SeonPhoneResponse {

  private PhoneData data;
  private Boolean success;
  private SeonResponseError error;

  public SeonPhoneResponse(SeonResponseError error) {
    this.error = error;
  }
}
