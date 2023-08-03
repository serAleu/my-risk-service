package asia.atmonline.myriskservice.web.seon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneData {

  private Boolean valid;
  private Long number;
  private String country;
  private Double score;
  private String carrier;
  private List<FlagsItem> flags;
  @JsonProperty("account_details")
  private AccountDetails accountDetails;
  private History history;
  private String id;
  private String type;
}
