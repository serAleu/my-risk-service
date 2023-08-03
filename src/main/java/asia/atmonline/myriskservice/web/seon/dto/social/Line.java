package asia.atmonline.myriskservice.web.seon.dto.social;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Line {

    private boolean registered;
    private String name;
    private String photo;
}
