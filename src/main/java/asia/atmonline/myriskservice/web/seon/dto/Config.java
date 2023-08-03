package asia.atmonline.myriskservice.web.seon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Config {

  private ConfigDetail ip;
  private ConfigDetail email;
  private ConfigDetail phone;

  @JsonProperty("ip_api")
  private boolean ipApi;

  @JsonProperty("email_api")
  private boolean emailApi;

  @JsonProperty("phone_api")
  private boolean phoneApi;

  @JsonProperty("device_fingerprinting")
  private boolean deviceFingerPrinting;
}
