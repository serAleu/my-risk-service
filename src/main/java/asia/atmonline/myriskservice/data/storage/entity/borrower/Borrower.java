package asia.atmonline.myriskservice.data.storage.entity.borrower;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressStateDictionary;
import asia.atmonline.myriskservice.enums.application.ApplicationsStep;
import asia.atmonline.myriskservice.enums.borrower.ExtraAttributes;
import asia.atmonline.myriskservice.enums.borrower.LoanPurpose;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.NotAudited;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "borrower", schema = "my-back")
@Getter
@Setter
public class Borrower extends UserAccount {

  @Embedded
  private PersonalData personalData;

  @Embedded
  private EmploymentData employmentData;

  @Embedded
  private IdentityData identityData;

  @Embedded
  private BankDetailsData bankDetailsData;

  @Embedded
  private UtmParametersData utmParametersData;

//  @Embedded
//  private ContactPersonData relative;

  @JoinColumn(name = "ad_state_id")
  @OneToOne(fetch = FetchType.EAGER)
  private AddressStateDictionary residenceState;

  @JoinColumn(name = "ad_city_id")
  @OneToOne(fetch = FetchType.EAGER)
  private AddressCityDictionary residenceCity;

  @Column(name = "residence_house_street")
  private String residenceHouseStreet;

  @Column(name = "bd_same_address", nullable = false, columnDefinition = "bool default false")
  private boolean sameAddress = false;

  @Column(name = "dpd_max", nullable = false, columnDefinition = "integer default 0")
  private Integer DPDMax;

  @Enumerated(EnumType.STRING)
  @Column(name = "loan_purpose")
  private LoanPurpose loanPurpose;

  @Column(name = "juicy_score_session_id")
  private String juicyScoreSessionId;

  @Column(name = "extra_attributes")
  @JdbcTypeCode(SqlTypes.JSON)
  @NotAudited
  private Map<ExtraAttributes, String> attributes;

  @Column(name = "last_application_step")
  @Enumerated(EnumType.STRING)
  private ApplicationsStep lastApplicationStep = ApplicationsStep.STEP_0;

  @Column(name = "last_application_step_updated_at")
  private LocalDateTime lastApplicationStepUpdatedAt = LocalDateTime.now();

  @Transient
  public String getName() {
    if (personalData != null) {
      return personalData.getFullName();
    } else {
      return "";
    }
  }

  public Map<ExtraAttributes, String> getAttributes() {
    if (this.attributes == null) {
      this.attributes = new EnumMap<>(ExtraAttributes.class);
    }
    return this.attributes;
  }

  public PersonalData getPersonalData() {
    if (this.personalData == null) {
      this.personalData = new PersonalData();
    }
    return personalData;
  }

  public UtmParametersData getUtmParametersData() {
    if (this.utmParametersData == null) {
      this.utmParametersData = new UtmParametersData();
    }
    return utmParametersData;
  }

  public void setUtmParametersData(UtmParametersData utmParametersData) {
    this.utmParametersData = utmParametersData;
  }

  public EmploymentData getEmploymentData() {
    if (this.employmentData == null) {
      this.employmentData = new EmploymentData();
    }
    return employmentData;
  }

  public IdentityData getIdentityData() {
    if (this.identityData == null) {
      this.identityData = new IdentityData();
    }
    return identityData;
  }

  public BankDetailsData getBankDetailsData() {
    if (this.bankDetailsData == null) {
      this.bankDetailsData = new BankDetailsData();
    }
    return bankDetailsData;
  }

  public String getBorrowerNIC() {
    return Optional.ofNullable(getIdentityData())
        .map(IdentityData::getPrimaryId)
        .orElse(StringUtils.EMPTY);
  }

  public boolean isOldNIC() {
    return getBorrowerNIC().toUpperCase().endsWith("V");
  }

  public String getAnotherVersionOfNIC() {
    return isOldNIC() ? getNewVersionNIC() : getOldVersionNIC();
  }

  private String getOldVersionNIC() {
    StringBuilder builder = new StringBuilder(getBorrowerNIC().substring(2));
    builder.deleteCharAt(5);
    builder.append("v");
    return builder.toString();
  }

  private String getNewVersionNIC() {
    StringBuilder builder = new StringBuilder(getBorrowerNIC());
    builder.deleteCharAt(builder.length() - 1);
    builder.insert(5, "0");
    String year = String.valueOf(getPersonalData().getBirthDate().getYear());
    builder.insert(0, year.substring(0, 2));
    return builder.toString();
  }

  public String getBorrowerAccount() {
    return Optional.ofNullable(getBankDetailsData())
        .map(BankDetailsData::getAccountNumber)
        .orElse(StringUtils.EMPTY);
  }

  public String getBorrowerPhone() {
    return Optional.ofNullable(getPersonalData())
        .map(PersonalData::getMobilePhone)
        .orElse(StringUtils.EMPTY);
  }

  public void setJuicyScoreSessionId(String juicyScoreSessionId) {
    this.juicyScoreSessionId = juicyScoreSessionId;
  }

//  @Override
//  public UserAccount setCreatedAt(LocalDateTime createdAt) {
//    return super.setCreatedAt(createdAt);
//  }

  @Override
  public LocalDateTime getCreatedAt() {
    return super.getCreatedAt();
  }
}
