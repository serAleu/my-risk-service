package asia.atmonline.myriskservice.data.risk.entity.external_responses.experian;

import asia.atmonline.myriskservice.data.risk.entity.BaseRiskJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@Entity
@Table(name = "credit_bureau_info_details")
@SequenceGenerator(name = "sequence-generator", sequenceName = "credit_bureau_info_details_id_seq", allocationSize = 1)
public class CreditBureauInfoDetails extends BaseRiskJpaEntity {

  @Column(name = "ccris_response_id", nullable = false)
  private Long ccrisResponseId;

  @Column(name = "master_id")
  private String masterId;

  @Column(name = "date")
  private String date;

  @Column(name = "capacity")
  private String capacity;

  @Column(name = "lender_type")
  private String lenderType;

  @Column(name = "credit_limit")
  private Long creditLimit;

  @Column(name = "legal_status")
  private String legalStatus;

  @Column(name = "legal_status_date")
  private String legalStatusDate;

  @Column(name = "master_collateral_type")
  private String masterCollateralType;

  @Column(name = "financial_group_resident_status")
  private String financialGroupResidentStatus;

  @Column(name = "master_collateral_type_code")
  private String masterCollateralTypeCode;

  @Column(name = "status")
  private String status;

  @Column(name = "restructure_reschedule_date")
  private String restructureRescheduleDate;

  @Column(name = "facility")
  private String facility;

  @Column(name = "total_outstanding_balance")
  private String totalOutstandingBalance;

  @Column(name = "total_outstanding_balance_bnm")
  private String totalOutstandingBalanceBnm;

  @Column(name = "balance_updated_date")
  private String balanceUpdatedDate;

  @Column(name = "installment_amount")
  private String installmentAmount;

  @Column(name = "principle_repayment_term")
  private String principleRepaymentTerm;

  @Column(name = "sub_account_collateral_type")
  private String subAccountCollateralType;

  @Column(name = "sub_account_collateral_type_code")
  private String subAccountCollateralTypeCode;

  @Column(name = "credit_position")
  private Integer creditPosition;

  @Override
  public String repositoryName() {
    return "creditBureauInfoDetailsJpaRepository";
  }
}
