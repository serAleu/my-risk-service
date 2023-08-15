package asia.atmonline.myriskservice.messages.request.impl;

import asia.atmonline.myriskservice.messages.request.BaseRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class SeonFraudRequest extends BaseRequest {

  @JsonProperty("borrower_id")
  private Long borrowerId;
  @JsonProperty("first_name")
  private String firstName;
  @JsonProperty("second_name")
  private String secondName;
  @JsonProperty("last_name")
  private String lastName;
  @JsonProperty("mobile_phone")
  private String mobilePhone;
  @JsonProperty("email")
  private String email;
  @JsonProperty("birth_date")
  private LocalDate birthDate;
  @JsonProperty("ip_address")
  private String ipAddress;
  @JsonProperty("created_at")
  private LocalDateTime createdAt;
  @JsonProperty("session_id")
  private String sessionId;
  @JsonProperty("session")
  private String session;
  @JsonProperty("seon_fraud_license_key")
  private String seonFraudLicenseKey;
  @JsonProperty("seon_fraud_base_url")
  private String seonFraudBaseUrl;
  @JsonProperty("seon_fraud_timeout")
  private Integer seonFraudTimeout;
  @JsonProperty("seon_fraud_requests_limit")
  private Long seonFraudRequestsLimit;
  @JsonProperty("seon_fraud_email_enable")
  private Boolean seonFraudEmailEnable;
  @JsonProperty("seon_fraud_email_api_version")
  private String seonFraudEmailApiVersion;
  @JsonProperty("seon_fraud_ip_api_version")
  private String seonFraudIpApiVersion;
  @JsonProperty("seon_fraud_phone_enable")
  private Boolean seonFraudPhoneEnable;
  @JsonProperty("seon_fraud_phone_api_version")
  private String seonFraudPhoneApiVersion;
  @JsonProperty("seon_fraud_phone_stop_factor_enable")
  private Boolean seonFraudPhoneStopFactorEnable;
  @JsonProperty("seon_fraud_fingerprint_enable")
  private Boolean seonFraudFingerprintEnable;

  @Transient
  public String getFullName() {
    return firstName + " " + secondName + " " + lastName;
  }
}