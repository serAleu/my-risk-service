package asia.atmonline.myriskservice.web.seon.dto.email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DomainDetail {

    private String domain;
    private String tld;
    private String created;
    private String updated;
    private String expires;
    private String registrarName;
    private String registeredTo;
    private Boolean disposable;
    private Boolean free;
    private Boolean custom;
    private Boolean dmarcEnforced;
    private Boolean spfStrict;
    private Boolean validMx;
    private Boolean acceptAll;
    private Boolean suspiciousTld;
    private Boolean websiteExists;
}
