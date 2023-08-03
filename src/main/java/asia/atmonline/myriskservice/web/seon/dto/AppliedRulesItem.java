package asia.atmonline.myriskservice.web.seon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppliedRulesItem {

  private Integer score;
  private String name;
  private String id;
  private String operation;
}
