package asia.atmonline.myriskservice.web.seon.dto.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Viber {

  @JsonProperty("last_seen")
  private Integer lastSeen;
  private boolean registered;
  private String photo;
  private String name;
}
