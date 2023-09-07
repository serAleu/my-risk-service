package asia.atmonline.myriskservice.data.storage.entity.borrower;


import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.HumanRaceDictionary;
import asia.atmonline.myriskservice.enums.borrower.MaritalStatus;
import asia.atmonline.myriskservice.enums.borrower.Sex;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Embeddable
public class PersonalData {

  public static final String PATTERN_DATEPICKER_FORMAT = "dd.MM.yyyy";

  @Column(name = "pd_first_name")
  private String fullName;

  @Column(name = "pd_sex")
  @Enumerated(EnumType.STRING)
  private Sex sex;

  @OneToOne
  @JoinColumn
  private HumanRaceDictionary race;

  @Column(name = "pd_marital_status")
  @Enumerated(EnumType.STRING)
  private MaritalStatus maritalStatus;

  @Column(name = "pd_mobile_phone", unique = true)
  private String mobilePhone;

  @Column(name = "pd_email")
  private String email;

  @Column(name = "pd_birth_date")
  @DateTimeFormat(pattern = PATTERN_DATEPICKER_FORMAT)
  private LocalDate birthDate;
}
