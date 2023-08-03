package asia.atmonline.myriskservice.web.seon.dto.social;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skype extends BaseSocialDto {
    private String photo;
    private String age;
    private String city;
    private String bio;
    private String country;
    private String gender;
    private String language;
    private String name;
    private String handle;
    private String id;
    private String state;
}
