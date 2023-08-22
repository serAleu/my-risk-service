package asia.atmonline.myriskservice.data.storage.entity.borrower;

import asia.atmonline.myriskservice.enums.application.ApplicationFormType;
import asia.atmonline.myriskservice.enums.application.ApplicationsStep;
import asia.atmonline.myriskservice.enums.borrower.LoanPurpose;
import asia.atmonline.myriskservice.enums.borrower.Occupation;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "borrower")
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
//  private ContactPersonData contactPerson1;
//
//  @Embedded
//  private ContactPersonData contactPerson2;

  @Embedded
  private CollateralData collateralData;

  @Embedded
  private AddressData registrationAddress;

//  @Embedded
//  private AddressData residenceAddress;

  @Column(name = "bd_same_address", nullable = false, columnDefinition = "bool default false")
  private boolean sameAddress = false;

//  private Set<PaymentCard> paymentCards;
//  private Set<Comment> comments;
//  private Set<Tag> tags;
//  private Set<IovationData> iovationData;

  @Column(name = "dpd_max", nullable = false, columnDefinition = "integer default 0")
  private Integer DPDMax;

  @Enumerated(EnumType.STRING)
  @Column(name = "form_type")
  private ApplicationFormType formType;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "filled_form_step")
//  private ApplicationFilledFormStep filledFormStep;

  @Enumerated(EnumType.STRING)
  @Column(name = "loan_purpose")
  private LoanPurpose loanPurpose;

  @Column(name = "juicy_score_session_id")
  private String juicyScoreSessionId;

//  @Column(name = "extra_attributes")
//  private Map<ExtraAttributes, String> attributes;

  @Column(name = "last_application_step")
  @Enumerated(EnumType.STRING)
  private ApplicationsStep lastApplicationStep = ApplicationsStep.STEP_0;

  @Column(name = "last_application_step_updated_at")
  private LocalDateTime lastApplicationStepUpdatedAt = LocalDateTime.now();

//  public AddressData getResidenceAddress() {
//    if (this.residenceAddress == null) {
//      this.residenceAddress = new AddressData();
//    }
//    return residenceAddress;
//  }

//  public void setResidenceAddress(AddressData residenceAddress) {
//    this.residenceAddress = residenceAddress;
//  }

//  public Set<IovationData> getIovationData() {
//    if (iovationData == null) {
//      iovationData = new HashSet<>();
//    }
//    return iovationData;
//  }

  @Transient
  public String getName() {
    if (personalData != null) {
      return personalData.getFirstName() + " " + personalData.getSecondName() + " " + personalData.getLastName();
    } else {
      return "";
    }
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

//  public ContactPersonData getContactPerson1() {
//    if (this.contactPerson1 == null) {
//      contactPerson1 = new ContactPersonData();
//    }
//    return contactPerson1;
//  }

  public CollateralData getCollateralData() {
    return this.collateralData != null ? this.collateralData : new CollateralData();
  }

//  public AddressData getRegistrationAddress() {
//    if (this.registrationAddress == null) {
//      this.registrationAddress = new AddressData();
//    }
//    return registrationAddress;
//  }

  public BigDecimal getBorrowerIncome() {
    return Optional.ofNullable(getEmploymentData()).map(EmploymentData::getIncome).orElse(BigDecimal.ZERO);
  }

  public int getBorrowerAge() {
    final LocalDate birthDate = Optional.ofNullable(getPersonalData()).map(PersonalData::getBirthDate).orElse(LocalDate.MIN);
    return Period.between(birthDate, LocalDate.now()).getYears();
  }

  public OccupationType getBorrowerOccupationType() {
    return Optional.ofNullable(getEmploymentData())
        .map(EmploymentData::getOccupationType)
        .orElse(OccupationType.UNEMPLOYED);
  }

  public WorkingIndustry getBorrowerWorkingIndustry() {
    return Optional.ofNullable(getEmploymentData())
        .map(EmploymentData::getWorkingIndustry)
        .orElse(null);
  }

  public String getBorrowerWorkingIndustryName() {
    return Optional.ofNullable(getEmploymentData().getWorkingIndustry())
            .map(WorkingIndustry::name).orElse(null);
  }

  public String getBorrowerOccupationName() {
    return Optional.ofNullable(getEmploymentData().getOccupation())
            .map(Occupation::name).orElse(null);
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

//  public String getBorrowerFirstContactPersonPhone() {
//    return Optional.ofNullable(getContactPerson1())
//        .map(ContactPersonData::getPhone)
//        .orElse(StringUtils.EMPTY);
//  }

//  public String getBorrowerSecondContactPersonPhone() {
//    return Optional.ofNullable(getContactPerson2())
//        .map(ContactPersonData::getPhone)
//        .orElse(StringUtils.EMPTY);
//  }

//  public String getBorrowerEmployerPhone() {
//    return Optional.ofNullable(getEmploymentData())
//        .map(EmploymentData::getEmployerPhone)
//        .orElse(StringUtils.EMPTY);
//  }

  public String getJuicyScoreSessionId() {
    return juicyScoreSessionId;
  }

  public void setJuicyScoreSessionId(String juicyScoreSessionId) {
    this.juicyScoreSessionId = juicyScoreSessionId;
  }

//  public Map<ExtraAttributes, String> getAttributes() {
//    if (this.attributes == null) {
//      this.attributes = new EnumMap<>(ExtraAttributes.class);
//    }
//    return this.attributes;
//  }
}
