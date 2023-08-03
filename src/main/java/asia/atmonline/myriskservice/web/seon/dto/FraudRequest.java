package asia.atmonline.myriskservice.web.seon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.LocalDate;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FraudRequest {

  private Config config;

  private String ip;
  private String email;

  @JsonProperty("email_domain")
  private String emailDomain;

  @JsonProperty("user_id")
  private Long userId;

  @JsonProperty("user_fullname")
  private String userfullname;

  @JsonProperty("user_dob")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate userDob;

  @JsonProperty("user_created")
  private Long userCreated;

  @JsonProperty("phone_number")
  private String phoneNumber;

  private String session;

  @JsonProperty("session_id")
  private String sessionId;

  @JsonProperty("user_country")
  private String userCountry;

  @JsonProperty("user_city")
  private String userCity;

  @JsonProperty("user_street")
  private String userStreet;

  @JsonProperty("custom_fields")
  private Map<String, String> customFields;

}
