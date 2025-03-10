package asia.atmonline.myriskservice.web.seon.dto.email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BreachDetail {
    private Boolean haveibeenpwnedListed;
    private Integer numberOfBreaches;
    private String firstBreach;
}
