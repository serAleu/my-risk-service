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
@Table(name = "credit_product", schema = "my-back")
public class CreditProduct extends BaseStorageEntity {

  @Column(name = "active")
  private boolean active = true;

  @Column(name = "external_id")
  @JsonIgnore
  private Long externalId;

  @Column(name = "title")
  private String title;

  @Column(name = "code")
  private String code;

  @Column(name = "engine_name")
  @JsonIgnore
  private String engineName;

  @Column(name = "min_amount")
//  @Digits(integer = 16, fraction = 0, message = NUMBER)
  private BigDecimal minAmount;

  @Column(name = "max_amount")
//  @Digits(integer = 16, fraction = 0, message = NUMBER)
  private BigDecimal maxAmount;

  @Enumerated(EnumType.STRING)
  @Column(name = "term_unit", nullable = false)
  private ChronoUnit termUnit;

  @Column(name = "min_term")
  private Integer minTerm;

  @Column(name = "max_term")
  private Integer maxTerm;

  @Column(name = "authorization_key")
  private String authorizationKey;

  @Column(name = "model_id")
  private String modelId;

  @Column(name = "external_scoring_service_result_timeout")
  private Integer externalScoringServiceResultTimeout;

  @Column(name = "next_product_code")
  private String nextProductCode;

  @Override
  public String toString() {
    return this.getTitle();
  }
}
