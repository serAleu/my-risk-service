package asia.atmonline.myriskservice.web.seon.dto.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Zalo {

  private boolean registered;
}
