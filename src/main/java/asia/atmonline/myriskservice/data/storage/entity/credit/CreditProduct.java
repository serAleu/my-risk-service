package asia.atmonline.myriskservice.data.storage.entity.credit;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "credit_product")
public class CreditProduct extends BaseStorageEntity {

  @Column(name = "active")
  private boolean active = true;

  @Column(name = "external_id")
  private Long externalId;

  @Column(name = "title")
  private String title;

  @Column(name = "code")
  private String code;

  @Column(name = "engine_name")
  private String engineName;

  @Column(name = "min_amount")
  private BigDecimal minAmount;

  @Column(name = "max_amount")
  private BigDecimal maxAmount;

  @Enumerated(EnumType.STRING)
  @Column(name = "term_unit", nullable = false)
  private ChronoUnit termUnit;

  @Column(name = "min_term")
  private Integer minTerm;

  @Column(name = "max_term")
  private Integer maxTerm;

  @Column(name = "min_finished_credits_count")
  @JsonIgnore
  private Integer minFinishedCreditsCount;

//  @Embedded
//  private ProlongationSettings prolongationSettings;
//
//  @Embedded
//  private CalculationSettings calculationSettings;
//
//  @Embedded
//  private RestructuringSettings restructuringSettings;

  @Column(name = "restructuring_agreement_template_id")
  private Long restructuringAgreementTemplateId;

  @Column(name = "grace_period_active", columnDefinition = "boolean default false")
  private boolean gracePeriodActive;

  @Column(name = "early_repayment_active", columnDefinition = "boolean default false")
  private boolean earlyRepaymentActive;

  @Column(name = "external_scoring_service_enabled", columnDefinition = "boolean default false")
  private boolean externalScoringServiceEnabled;

  @Column(name = "external_scoring_decision", columnDefinition = "boolean default false")
  private boolean externalScoringDecision;

  @Column(name = "scoring_service_url")
  private String scoringServiceUrl;

  @Column(name = "authorization_key")
  private String authorizationKey;

  @Column(name = "model_id")
  private String modelId;

  @Column(name = "external_scoring_service_result_timeout")
  private Integer externalScoringServiceResultTimeout;

  @Override
  public String toString() {
    return this.getTitle();
  }

}
