package asia.atmonline.myriskservice.data.risk.entity.blacklists;

import asia.atmonline.myriskservice.data.risk.entity.BaseRiskJpaEntity;
import asia.atmonline.myriskservice.enums.risk.BlacklistSource;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BlacklistBaseRiskJpaEntity extends BaseRiskJpaEntity {

  @Column(name = "added_by")
  private Long addedBy;

  @Column(name = "added_at")
  private LocalDateTime addedAt;

  @Column(name = "expired_at")
  private LocalDateTime expiredAt;

  @Column(name = "credit_application_id")
  private Long creditApplicationId;

  @Column(name = "bl_level")
  private Integer blLevel;

  @Column(name = "bl_reason")
  private String blReason;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "rule_id")
  private BlacklistRule rule;

  @Column(name = "source")
  @Enumerated(EnumType.STRING)
  private BlacklistSource source;

}