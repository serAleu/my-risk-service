package asia.atmonline.myriskservice.data.storage.entity.borrower;


import asia.atmonline.myriskservice.enums.borrower.MaritalStatus;
import asia.atmonline.myriskservice.enums.borrower.Sex;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PersonalData {

  @Column(name = "pd_first_name")
  private String firstName;

  @Column(name = "pd_second_name")
  private String secondName;

  @Column(name = "pd_last_name")
  private String lastName;

  @Column(name = "pd_sex")
  @Enumerated(EnumType.STRING)
  private Sex sex;

  @Column(name = "pd_marital_status")
  @Enumerated(EnumType.STRING)
  private MaritalStatus maritalStatus;

  @Column(name = "pd_mobile_phone", unique = true)
  private String mobilePhone;

  @Column(name = "pd_email")
  private String email;

  @Column(name = "pd_birth_date")
  private LocalDate birthDate;

  @Column(name = "pd_amount_of_children")
  private String amountOfChildren;

  @Transient
  public String getFullName() {
    return firstName + " " + secondName + " " + lastName;
  }
}
