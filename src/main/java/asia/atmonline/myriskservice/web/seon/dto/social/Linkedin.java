package asia.atmonline.myriskservice.web.seon.dto.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Linkedin extends BaseSocialDto {
    private String url;
    private String name;
    private String company;
    private String title;
    private String location;
    private String website;
    private String twitter;
    private String photo;
}
