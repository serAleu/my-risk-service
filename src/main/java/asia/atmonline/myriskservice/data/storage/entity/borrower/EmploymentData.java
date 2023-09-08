package asia.atmonline.myriskservice.data.storage.entity.borrower;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class EmploymentData implements Serializable {

  @JoinColumn(name = "ed_occupation_type_id")
  @OneToOne(fetch = FetchType.EAGER)
  private OccupationTypeDictionary occupationType;

  @JoinColumn(name = "ed_working_industry_id")
  @OneToOne(fetch = FetchType.EAGER)
  private WorkingIndustryDictionary workingIndustry;

//  @JoinColumn(name = "ed_working_time_id")
//  @OneToOne(fetch = FetchType.LAZY)
//  private WorkingTimeDictionary workingTime;

  @Column(name = "ed_employer_name")
  private String employerName;

  @Column(name = "ed_employer_phone")
  private String employerPhone;

  @Column(name = "ed_employer_website")
  private String employerWebsite;

  @Column(name = "ed_income", precision = 19)
  private BigDecimal income;

  @Column(name = "ed_job_position")
  private String jobPosition;
}
