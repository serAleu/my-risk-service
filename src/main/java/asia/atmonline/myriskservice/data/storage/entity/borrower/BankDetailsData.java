package asia.atmonline.myriskservice.data.storage.entity.borrower;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class BankDetailsData {

//  @Column(name = "bd_bank_name")
//  private String bankName;

  @Column(name = "bd_bank_id")
  private Long bankId;

//  @Column(name = "bd_bank_city")
//  private String bankCity;
//
//  @Column(name = "bd_branch")
//  private String branch;
//
//  @Column(name = "bd_bank_branch_id")
//  private Long bankBranchId;
//
//  @Column(name = "bd_provide_bank_details_later", nullable = false, columnDefinition = "bool default false")
//  private boolean provideBankDetailsLater = false;
//
  @Column(name = "bd_account_number")
  private String accountNumber;
//
//  @Column(name = "bd_conditions", nullable = false, columnDefinition = "bool default false")
//  private boolean conditions;
//
//  @Column(name = "bd_validation_concent", nullable = false, columnDefinition = "bool default false")
//  private boolean validationConcent;
//
//  @Column(name = "bd_provide_photo_later", nullable = false, columnDefinition = "bool default false")
//  private boolean providePhotoLater;
}
