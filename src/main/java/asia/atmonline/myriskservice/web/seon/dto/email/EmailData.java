package asia.atmonline.myriskservice.web.seon.dto.email;

import asia.atmonline.myriskservice.web.seon.dto.FlagsItem;
import asia.atmonline.myriskservice.web.seon.dto.History;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EmailData {
    private String email;
    private Double score;
    private Boolean deliverable;
    private DomainDetail domainDetails;
    private AccountDetails accountDetails;
    private BreachDetail breachDetails;
    private String id;
    private List<FlagsItem> flags;
    private History history;

}
