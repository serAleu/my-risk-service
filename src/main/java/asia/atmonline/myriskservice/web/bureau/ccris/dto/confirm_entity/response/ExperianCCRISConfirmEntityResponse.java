package asia.atmonline.myriskservice.web.bureau.ccris.dto.confirm_entity.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExperianCCRISConfirmEntityResponse {

  @JsonProperty("token1")
  private String token1;
  @JsonProperty("token2")
  private String token2;
}
