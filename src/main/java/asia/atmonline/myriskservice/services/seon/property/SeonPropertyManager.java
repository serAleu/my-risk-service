package asia.atmonline.myriskservice.services.seon.property;

import asia.atmonline.myriskservice.data.storage.entity.property.SystemProperty;
import asia.atmonline.myriskservice.data.storage.repositories.property.SystemPropertyJpaRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SeonPropertyManager {

  @Value("${seon-fraud.license-key}")
  private String seonFraudLicenseKey;
  @Value("${seon-fraud.base-url}")
  private String seonFraudBaseUrl;
  @Value("${seon-fraud.paths-to-properties.timeout}")
  private String seonFraudPathToTimeout;
  @Value("${seon-fraud.paths-to-properties.requests-limit}")
  private String seonFraudPathToRequestLimit;
  @Value("${seon-fraud.paths-to-properties.ip.api-version}")
  private String seonFraudPathToIpApiVersion;
  @Value("${seon-fraud.paths-to-properties.email.enable}")
  private String seonFraudPathToEmailEnable;
  @Value("${seon-fraud.paths-to-properties.email.api-version}")
  private String seonFraudPathToEmailApiVersion;
  @Value("${seon-fraud.paths-to-properties.phone.enable}")
  private String seonFraudPathToPhoneEnable;
  @Value("${seon-fraud.paths-to-properties.phone.api-version}")
  private String seonFraudPathToPhoneApiVersion;
  @Value("${seon-fraud.paths-to-properties.phone.stop-factor.enable}")
  private String seonFraudPathToPhoneStopFactorEnable;
  @Value("${seon-fraud.paths-to-properties.fingerprint.enable}")
  private String seonFraudPathToFingerprintEnable;
  @Value("${seon-fraud.default.timeout}")
  private Integer seonFraudDefaultTimeout;
  @Value("${seon-fraud.default.requests-limit}")
  private Long seonFraudDefaultRequestLimit;
  @Value("${seon-fraud.default.ip.api-version}")
  private String seonFraudDefaultIpApiVersion;
  @Value("${seon-fraud.default.email.enable}")
  private Boolean seonFraudDefaultEmailEnable;
  @Value("${seon-fraud.default.email.api-version}")
  private String seonFraudDefaultEmailApiVersion;
  @Value("${seon-fraud.default.phone.enable}")
  private Boolean seonFraudDefaultPhoneEnable;
  @Value("${seon-fraud.default.phone.api-version}")
  private String seonFraudDefaultPhoneApiVersion;
  @Value("${seon-fraud.default.phone.stop-factor.enable}")
  private Boolean seonFraudDefaultPhoneStopFactorEnable;
  @Value("${seon-fraud.default.fingerprint.enable}")
  private Boolean seonFraudDefaultFingerprintEnable;

  private final SystemPropertyJpaRepository systemPropertyJpaRepository;

  public SeonPropertyManager(SystemPropertyJpaRepository systemPropertyJpaRepository) {
    this.systemPropertyJpaRepository = systemPropertyJpaRepository;
  }

  public String getSeonFraudLicenseKey() {
    return seonFraudLicenseKey;
  }

  public String getSeonFraudBaseUrl() {
    return seonFraudBaseUrl;
  }

  public Integer getSeonFraudTimeout() {
//    Optional<SystemProperty> property = systemPropertyJpaRepository.findByPropertyKey(seonFraudPathToTimeout);
    Optional<SystemProperty> property = null;
    return property.map(systemProperty -> Integer.parseInt(systemProperty.getValue())).orElseGet(() -> seonFraudDefaultTimeout);
  }

  public Long getSeonFraudRequestLimit() {
//    Optional<SystemProperty> property = systemPropertyJpaRepository.findByPropertyKey(seonFraudPathToRequestLimit);
    Optional<SystemProperty> property = null;
    return property.map(systemProperty -> Long.parseLong(systemProperty.getValue())).orElseGet(() -> seonFraudDefaultRequestLimit);
  }

  public String getSeonFraudIpApiVersion() {
//    Optional<SystemProperty> property = systemPropertyJpaRepository.findByPropertyKey(seonFraudPathToIpApiVersion);
    Optional<SystemProperty> property = null;
    return property.map(SystemProperty::getValue).orElseGet(() -> seonFraudDefaultIpApiVersion);
  }

  public Boolean getSeonFraudEmailEnable() {
//    Optional<SystemProperty> property = systemPropertyJpaRepository.findByPropertyKey(seonFraudPathToEmailEnable);
    Optional<SystemProperty> property = null;
    return property.map(systemProperty -> Boolean.parseBoolean(systemProperty.getValue())).orElseGet(() -> seonFraudDefaultEmailEnable);
  }

  public String getSeonFraudEmailApiVersion() {
//    Optional<SystemProperty> property = systemPropertyJpaRepository.findByPropertyKey(seonFraudPathToEmailApiVersion);
    Optional<SystemProperty> property = null;
    return property.map(SystemProperty::getValue).orElseGet(() -> seonFraudDefaultEmailApiVersion);
  }

  public Boolean getSeonFraudPhoneEnable() {
//    Optional<SystemProperty> property = systemPropertyJpaRepository.findByPropertyKey(seonFraudPathToPhoneEnable);
    Optional<SystemProperty> property = null;
    return property.map(systemProperty -> Boolean.parseBoolean(systemProperty.getValue())).orElseGet(() -> seonFraudDefaultPhoneEnable);
  }

  public String getSeonFraudPhoneApiVersion() {
//    Optional<SystemProperty> property = systemPropertyJpaRepository.findByPropertyKey(seonFraudPathToPhoneApiVersion);
    Optional<SystemProperty> property = null;
    return property.map(SystemProperty::getValue).orElseGet(() -> seonFraudDefaultPhoneApiVersion);
  }

  public Boolean getSeonFraudPhoneStopFactorEnable() {
//    Optional<SystemProperty> property = systemPropertyJpaRepository.findByPropertyKey(seonFraudPathToPhoneStopFactorEnable);
    Optional<SystemProperty> property = null;
    return property.map(systemProperty -> Boolean.parseBoolean(systemProperty.getValue())).orElseGet(() -> seonFraudDefaultPhoneStopFactorEnable);
  }

  public Boolean getSeonFraudFingerprintEnable() {
//    Optional<SystemProperty> property = systemPropertyJpaRepository.findByPropertyKey(seonFraudPathToFingerprintEnable);
    Optional<SystemProperty> property = null;
    return property.map(systemProperty -> Boolean.parseBoolean(systemProperty.getValue())).orElseGet(() -> seonFraudDefaultFingerprintEnable);
  }

//  private Map<String, SystemProperty> fillPropertyMap() {
//    List<String> paths = List.of(seonFraudPathToTimeout, seonFraudPathToRequestLimit, seonFraudPathToIpApiVersion, seonFraudPathToEmailEnable,
//        seonFraudPathToEmailApiVersion, seonFraudPathToPhoneEnable, seonFraudPathToPhoneApiVersion, seonFraudPathToPhoneStopFactorEnable,
//        seonFraudPathToFingerprintEnable);
//    Map<String, SystemProperty> propertyMap = new HashMap<>();
//    paths.forEach(path -> systemPropertyJpaRepository.findByPropertyKey(path).ifPresent(property -> propertyMap.put(path, property)));
//    return propertyMap;
//  }
}
