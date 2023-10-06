package asia.atmonline.myriskservice.data.storage.entity.application;

import asia.atmonline.myriskservice.data.storage.entity.BaseCreditEntity;
import asia.atmonline.myriskservice.data.storage.entity.borrower.CpaUtmData;
import asia.atmonline.myriskservice.data.storage.entity.borrower.UtmParametersData;
import asia.atmonline.myriskservice.enums.borrower.LoanPurpose;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.JdbcTypeCode;
//import org.hibernate.envers.NotAudited;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@Table(name = "credit_application", schema = "my-back")
@BatchSize(size = BaseCreditEntity.DEFAULT_BATCH_SIZE)
//@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class CreditApplication extends BaseCreditEntity {

  @Column(name = "requested_at", nullable = false)
  private LocalDateTime requestedAt;

  @Column(name = "requested_term")
  private Integer requestedTerm;

  @Column(name = "approved_term")
  private Integer approvedTerm;

  @Column(name = "requested_amount")
  private BigDecimal requestedAmount;

  @Column(name = "approved_amount")
  private BigDecimal approvedAmount;

  @Column(name = "available_limit")
  private BigDecimal availableLimit;

  @Column(name = "purpose")
  @Enumerated(EnumType.STRING)
  private LoanPurpose purpose;

//  @NotAudited
  @Column(name = "ip_address")
  private String ipAddress;

//  @Column(name = "status")
//  @Enumerated(EnumType.STRING)
//  private CreditApplicationStatus status;

  @Column(name = "status")
  private String status;

  @Column(name = "operator_id")
  private Long operator;

  @Column(name = "office_id")
  private Long office;

  @Column(name = "underwriter_id")
  private Long underwriter;

  @Column(name = "outgoing_payment_provider")
  private String outgoingPaymentProvider;

//  @NotAudited
  @Column(name = "outgoing_transaction_id")
  private Long outgoingTransaction;

  @Embedded
//  @NotAudited
  private UtmParametersData utmParametersData;

  @Embedded
//  @NotAudited
  private CpaUtmData cpaUtmData;

  @Column(name = "underwriter_decision_made_at")
  private LocalDateTime underwriterDecisionMadeAt;

  @Column(name = "offer_signed_at")
  private LocalDateTime offerSignedAt;

  @Column(name = "contract_signed_at")
  private LocalDateTime contractSignedAt;

//  @NotAudited
  @Column(name = "trial")
  private boolean trial = false;

//  @NotAudited
  @Column(name = "notification_sent")
  private boolean notificationSent = false;

//  @NotAudited
  @Column(name = "lender_signed")
  private Boolean signedByLeander;

//  @NotAudited
  @Column(name = "device_info_id")
  private Long deviceInfo;

//  @NotAudited
  @Column(name = "decision_log", columnDefinition = "jsonb default '{}'")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Map<String, Object>> decisionData;

//  @NotAudited
  @Column(name = "rejection_reason", columnDefinition = "jsonb default '{}'")
  @JdbcTypeCode(SqlTypes.JSON)
  private RejectionReason rejectionReason;

//  @NotAudited
  @Column(name = "kyc_checked")
  private boolean kycChecked = false;

//  @NotAudited
  @Column(name = "email_verified")
  private boolean emailVerified = false;

//  @NotAudited
  @Column(name = "rejection_reason_code")
  private String rejectionReasonCode;

//  @NotAudited
  @Column(name = "dms_report", columnDefinition = "jsonb default '{}'")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> dmsReport;

//  @NotAudited
  @Column(name = "cpa_request_status")
  private String cpaRequestStatus;

//  @NotAudited
  @Column(name = "repaid_loan_amount")
  private Long repaidLoanAmount;

//  @NotAudited
  @Column(name = "domain")
  private String domain;

  @Column(name = "limit_decision")
  private String limitDecision;

  @Column(name = "min_loan_limit")
  private BigDecimal minLoanLimit;

  @Column(name = "max_loan_limit")
  private BigDecimal maxLoanLimit;

//  @NotAudited
  @Column(name = "borrower_data_changed")
  private Boolean borrowerDataChanged;

//  @NotAudited
  @Column(name = "pv_dms_check_successful")
  private Boolean pvDmsCheckSuccessful;

//  @NotAudited
  @Column(name = "strategy_type")
  private String strategyType;

//  @NotAudited
  @Column(name = "score1")
  private BigDecimal score1;

//  @NotAudited
  @Column(name = "score2")
  private String score2;

//  @NotAudited
  @Column(name = "score2_script_version")
  private String score2ScriptVersion;

//  @NotAudited
  @Column(name = "score3")
  private String score3;

//  @NotAudited
  @Column(name = "score3_script_version")
  private String score3ScriptVersion;

//  @NotAudited
  @Column(name = "script_log", columnDefinition = "jsonb default '[]'")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<String> scriptLog = new ArrayList<>();

  @Transient
  private Long contractId;

//  @NotAudited
  @Column(name = "auto_disbursement_passed")
  private boolean autoDisbursementPassed = false;

//  @NotAudited
  @Column(name = "dms_strategy_type")
  private String dmsStrategyType;

//  @NotAudited
  @Column(name = "juicyscore_response_id")
  private Long juicyScoreResponseId;

//  @NotAudited
  @Column(name = "seon_phone_response_id")
  private Long seonPhoneResponseId;

//  @NotAudited
//  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  @JoinColumn(name = "application_id")
//  @OrderBy("id DESC")
//  private List<CreditApplicationFollowUp> followUps;
//
//  @NotAudited
//  @OneToMany(fetch = FetchType.LAZY)
//  @JoinColumn(name = "application_id", updatable = false, insertable = false)
//  private Set<CreditApplicationFollowUpView> followUpsView;

//  @NotAudited
  @Column(name = "express_processing")
  private boolean expressProcessing;

//  @Transient
//  private FollowUpType followUpType;

  @Transient
  private LocalDateTime followUpReminderTime;

//  @NotAudited
  @Column(name = "score_results")
  private Integer scoreResult;

//  @NotAudited
  @Column(name = "reference_number")
  private String referenceNumber;

//  @NotAudited
  @Column(name = "score_model_id")
  private String scoreModelId;

//  @NotAudited
  @Column(name = "score_script_version")
  private String scoreScriptVersion;

  @Column(name = "dms_approved_max_amount")
  private BigDecimal dmsApprovedMaxAmount;

//  @NotAudited
  @Column(name = "pilot")
  private String pilot;

//  @NotAudited
  @Column(name = "external_score")
  private Integer externalScore;

//  @NotAudited
  @Column(name = "scoring_service_response")
  private String scoringServiceResponse;

//  @NotAudited
  @Column(name = "scoring_service_error")
  private String scoringServiceError;

//  @NotAudited
  @Column(name = "scoring_service_responses", columnDefinition = "jsonb default '[]'")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<Object> scoringServiceResponses;

//  @NotAudited
  @Column(name = "external_score_requested_at")
  private LocalDateTime externalScoreRequestedAt;

//  @NotAudited
  @Column(name = "score_model_version")
  private String scoreModelVersion;

  public boolean isFollowUpReminderTimeExpired() {
    return followUpReminderTime != null && followUpReminderTime.isBefore(LocalDateTime.now());
  }

  public void addPilotItem(String pilotItem) {
    Set<String> pilotList = new HashSet<>();
    if (this.pilot != null) {
      pilotList = new HashSet<>(Arrays.asList(this.pilot.split(",")));
    }
    pilotList.add(pilotItem);
    pilotList.removeAll(Collections.singleton(""));
    this.pilot = String.join(",", pilotList);
  }

  public void removePilotItem(String pilotItem) {
    Set<String> pilotList = new HashSet<>();
    if (this.pilot != null) {
      pilotList = new HashSet<>(Arrays.asList(this.pilot.split(",")));
    }
    pilotList.remove(pilotItem);
    pilotList.removeAll(Collections.singleton(""));
    this.pilot = String.join(",", pilotList);
  }
}
