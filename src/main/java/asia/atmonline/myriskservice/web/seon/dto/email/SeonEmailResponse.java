package asia.atmonline.myriskservice.web.seon.dto.email;

import asia.atmonline.myriskservice.web.seon.dto.SeonResponseError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class SeonEmailResponse {
    private EmailData emailData;
    private Boolean success;
    private SeonResponseError error;

    public SeonEmailResponse(SeonResponseError error) {
        this.error = error;
    }
}
