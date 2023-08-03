package asia.atmonline.myriskservice.web.seon.dto.social;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseSocialDto {
    private boolean registered;
}
