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
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@Table(name = "credit_application", schema = "my-back")
@BatchSize(size = BaseCreditEntity.DEFAULT_BATCH_SIZE)
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

  @Column(name = "ip_address")
  private String ipAddress;

//  @Column(name = "status")
//  @Enumerated(EnumType.STRING)
//  @JdbcTypeCode(SqlTypes.VARCHAR)
//  private CreditApplicationStatus status;

  @Column(name = "status")
  private Integer status;

  @Column(name = "operator_id")
  private Long operator;

  @Column(name = "office_id")
  private Long office;

  @Column(name = "underwriter_id")
  private Long underwriter;

  @Column(name = "outgoing_payment_provider")
  private String outgoingPaymentProvider;

  @Column(name = "outgoing_transaction_id")
  private Long outgoingTransaction;

  @Embedded
  private UtmParametersData utmParametersData;

  @Embedded
  private CpaUtmData cpaUtmData;

  @Column(name = "underwriter_decision_made_at")
  private LocalDateTime underwriterDecisionMadeAt;

  @Column(name = "offer_signed_at")
  private LocalDateTime offerSignedAt;

  @Column(name = "contract_signed_at")
  private LocalDateTime contractSignedAt;

  @Column(name = "trial")
  private boolean trial = false;

  @Column(name = "notification_sent")
  private boolean notificationSent = false;

  @Column(name = "lender_signed")
  private Boolean signedByLeander;

  @Column(name = "device_info_id")
  private Long deviceInfo;

  @Column(name = "decision_log", columnDefinition = "jsonb default '{}'")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Map<String, Object>> decisionData;

  @Column(name = "rejection_reason", columnDefinition = "jsonb default '{}'")
  @JdbcTypeCode(SqlTypes.JSON)
  private RejectionReason rejectionReason;

  @Column(name = "kyc_checked")
  private boolean kycChecked = false;

  @Column(name = "email_verified")
  private boolean emailVerified = false;

  @Column(name = "rejection_reason_code")
  private String rejectionReasonCode;

  @Column(name = "dms_report", columnDefinition = "jsonb default '{}'")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> dmsReport;

  @Column(name = "cpa_request_status")
  private String cpaRequestStatus;

  @Column(name = "repaid_loan_amount")
  private Long repaidLoanAmount;

  @Column(name = "domain")
  private String domain;

  @Column(name = "limit_decision")
  private String limitDecision;

  @Column(name = "min_loan_limit")
  private BigDecimal minLoanLimit;

  @Column(name = "max_loan_limit")
  private BigDecimal maxLoanLimit;

  @Column(name = "borrower_data_changed")
  private Boolean borrowerDataChanged;

  @Column(name = "pv_dms_check_successful")
  private Boolean pvDmsCheckSuccessful;

  @Column(name = "strategy_type")
  private String strategyType;

  @Column(name = "score1")
  private BigDecimal score1;

  @Column(name = "score2")
  private String score2;

  @Column(name = "score2_script_version")
  private String score2ScriptVersion;

  @Column(name = "score3")
  private String score3;

  @Column(name = "score3_script_version")
  private String score3ScriptVersion;

  @Column(name = "script_log", columnDefinition = "jsonb default '[]'")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<String> scriptLog = new ArrayList<>();

  @Transient
  private Long contractId;

  @Column(name = "auto_disbursement_passed")
  private boolean autoDisbursementPassed = false;

  @Column(name = "dms_strategy_type")
  private String dmsStrategyType;

  @Column(name = "juicyscore_response_id")
  private Long juicyScoreResponseId;

  @Column(name = "seon_phone_response_id")
  private Long seonPhoneResponseId;

//  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  @JoinColumn(name = "application_id")
//  @OrderBy("id DESC")
//  private List<CreditApplicationFollowUp> followUps;
//
//  @OneToMany(fetch = FetchType.LAZY)
//  @JoinColumn(name = "application_id", updatable = false, insertable = false)
//  private Set<CreditApplicationFollowUpView> followUpsView;

  @Column(name = "express_processing")
  private boolean expressProcessing;

//  @Transient
//  private FollowUpType followUpType;

  @Transient
  private LocalDateTime followUpReminderTime;

  @Column(name = "score_results")
  private Integer scoreResult;

  @Column(name = "reference_number")
  private String referenceNumber;

  @Column(name = "score_model_id")
  private String scoreModelId;

  @Column(name = "score_script_version")
  private String scoreScriptVersion;

  @Column(name = "dms_approved_max_amount")
  private BigDecimal dmsApprovedMaxAmount;

  @Column(name = "pilot")
  private String pilot;

  @Column(name = "external_score")
  private Integer externalScore;

  @Column(name = "scoring_service_response")
  private String scoringServiceResponse;

  @Column(name = "scoring_service_error")
  private String scoringServiceError;

  @Column(name = "scoring_service_responses", columnDefinition = "jsonb default '[]'")
  @JdbcTypeCode(SqlTypes.JSON)
  private List<Object> scoringServiceResponses;

  @Column(name = "external_score_requested_at")
  private LocalDateTime externalScoreRequestedAt;

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
