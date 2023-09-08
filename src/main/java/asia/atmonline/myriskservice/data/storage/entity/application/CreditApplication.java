package asia.atmonline.myriskservice.data.storage.entity.application;

import asia.atmonline.myriskservice.data.storage.entity.BaseCreditEntity;
import asia.atmonline.myriskservice.enums.application.CreditApplicationStatus;
import asia.atmonline.myriskservice.enums.borrower.LoanPurpose;
import jakarta.persistence.Column;
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
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Setter
@Table(name = "credit_application", schema = "my-back")
public class CreditApplication extends BaseCreditEntity {

  @Column(name = "requested_at", nullable = false)
  private LocalDateTime requestedAt;

  @Column(name = "requested_term")
  private Integer requestedTerm;

  @Column(name = "approved_term")
  private Integer approvedTerm;

  @Column(name = "requested_amount", precision = 19, nullable = false)
  private BigDecimal requestedAmount;

  @Column(name = "approved_amount", precision = 19)
  private BigDecimal approvedAmount;

  @Column(name = "available_limit", precision = 19)
  private BigDecimal availableLimit;

  @Column(name = "purpose")
  @Enumerated(EnumType.STRING)
  private LoanPurpose purpose;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "status", nullable = false)
  private CreditApplicationStatus status;

//  @JoinColumn(name = "operator_id")
//  @ManyToOne(fetch = FetchType.EAGER)
//  private BackOfficeUserAccount operator;

//  @JoinColumn(name = "office_id")
//  @ManyToOne(fetch = FetchType.LAZY)
//  private Office office;

//  @ManyToOne(fetch = FetchType.EAGER)
//  @JoinColumn(name = "underwriter_id")
//  private BackOfficeUserAccount underwriter;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "outgoing_payment_provider")
//  private PaymentProvider outgoingPaymentProvider;

//  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "outgoing_transaction_id")
//  private PaymentTransaction outgoingTransaction;

//  @Embedded
//  @NotAudited
//  private UtmParametersData utmParametersData;

//  @Embedded
//  @NotAudited
//  private CpaUtmData cpaUtmData;

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

//  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "device_info_id")
//  @JsonIgnore
//  private DeviceInfo deviceInfo;

//  @Column(name = "decision_log", columnDefinition = "jsonb default '{}'")
//  private Map<String, Map<String, Object>> decisionData;

//  @Column(name = "rejection_reason", columnDefinition = "jsonb default '{}'")
//  @Type(type = "JsonDataUserRejectionReason")
//  private RejectionReason rejectionReason;

  @Column(name = "kyc_checked", columnDefinition = "boolean default false")
  private boolean kycChecked = false;

  @Column(name = "email_verified", columnDefinition = "boolean default false")
  private boolean emailVerified = false;

  @Column(name = "rejection_reason_code")
  private String rejectionReasonCode;

//  @Column(name = "dms_report", columnDefinition = "jsonb default '{}'")
//  private Map<String, Object> dmsReport;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "cpa_request_status")
//  private CpaRequestStatus cpaRequestStatus;

  @Column(name = "repaid_loan_amount", nullable = false, updatable = false, columnDefinition = "bigint default 0")
  private long repaidLoanAmount;

  @Column(name = "domain")
  private String domain;

  @Column(name = "limit_decision")
  private String limitDecision;

  @Column(name = "min_loan_limit")
  private BigDecimal minLoanLimit;

  @Column(name = "max_loan_limit")
  private BigDecimal maxLoanLimit;

  @Column(name = "borrower_data_changed", columnDefinition = "bool default false")
  private boolean borrowerDataChanged;

  @Column(name = "pv_dms_check_successful", columnDefinition = "bool default false")
  private boolean pvDmsCheckSuccessful;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "strategy_type")
//  private Score1StrategyType strategyType;

  @Column(name = "score1", scale = 17)
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

//  @Enumerated(EnumType.STRING)
//  @Column(name = "dms_strategy_type")
//  private DmsStrategyType dmsStrategyType;

  @Column(name = "juicyscore_response_id")
  private Long juicyScoreResponseId;

  @Column(name = "seon_phone_response_id")
  private Long seonPhoneResponseId;

//  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  @JoinColumn(name = "application_id")
//  @OrderBy("id DESC")
//  private List<CreditApplicationFollowUp> followUps;

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

  @XmlElement(required = true)
  @Column(name = "dms_approved_max_amount", precision = 19)
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

  @Column(name = "external_score_requested_at", nullable = false)
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
