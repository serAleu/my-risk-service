package asia.atmonline.myriskservice.web.bureau.ccris.dto.search.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExperianCCRISSearchResponse {

  @JsonProperty("ccris_identity")
  private List<ExperianCCRISIdentityResponse> CCrisIdentities;
}
