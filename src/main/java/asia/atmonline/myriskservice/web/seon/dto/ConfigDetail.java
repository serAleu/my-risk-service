package asia.atmonline.myriskservice.web.seon.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConfigDetail {

  private String include;
  private String version;
}