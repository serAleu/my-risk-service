package asia.atmonline.myriskservice.web.seon.dto.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ok extends BaseSocialDto {

  private String city;
  private Integer age;
  private LocalDateTime dateJoined;
}
