package asia.atmonline.myriskservice.data.storage.entity.borrower;

import asia.atmonline.myriskservice.enums.borrower.Occupation;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class EmploymentData implements Serializable {

  @Enumerated(EnumType.STRING)
  @Column(name = "ed_occupation_type")
  private OccupationType occupationType;

  @Enumerated(EnumType.STRING)
  @Column(name = "ed_occupation")
  private Occupation occupation;

  @Enumerated(EnumType.STRING)
  @Column(name = "ed_working_industry")
  private WorkingIndustry workingIndustry;

  @Column(name = "ed_employer_name")
  private String employerName;

  @Column(name = "ed_employer_phone")
  private String employerPhone;

  @Column(name = "ed_employer_address")
  private String employerAddress;

  @Column(name = "ed_income", precision = 19)
  private BigDecimal income;
}
