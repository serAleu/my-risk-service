package asia.atmonline.myriskservice.web.seon.dto.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airbnb extends BaseSocialDto {
    private String about;
    private LocalDateTime createdAt;
    private String firstName;
    private String identityVerified;
    private String location;
    private String image;
    private Integer revieweeCount;
    private String trips;
    private String work;
}
