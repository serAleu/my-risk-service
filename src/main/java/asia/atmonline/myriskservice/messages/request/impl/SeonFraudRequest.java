package asia.atmonline.myriskservice.messages.request.impl;

import asia.atmonline.myriskservice.messages.request.BaseRequest;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Accessors(chain = true)
public class SeonFraudRequest extends BaseRequest {

  private Long borrowerId;
  private String firstName;
  private String secondName;
  private String lastName;
  private String mobilePhone;
  private String email;
  private LocalDate birthDate;
  private String ipAddress;
  private LocalDateTime createdAt;
  private String sessionId;
  private String session;
  private String seonFraudLicenseKey;
  private String seonFraudBaseUrl;
  private Integer seonFraudTimeout;
  private Long seonFraudRequestsLimit;
  private Boolean seonFraudEmailEnable;
  private String seonFraudEmailApiVersion;
  private String seonFraudIpApiVersion;
  private Boolean seonFraudPhoneEnable;
  private String seonFraudPhoneApiVersion;
  private Boolean seonFraudPhoneStopFactorEnable;
  private Boolean seonFraudFingerprintEnable;

  @Transient
  public String getFullName() {
    return firstName + " " + secondName + " " + lastName;
  }
}