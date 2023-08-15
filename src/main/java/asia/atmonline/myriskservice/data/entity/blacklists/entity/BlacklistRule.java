package asia.atmonline.myriskservice.data.entity.blacklists.entity;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "blacklist_rule")
@Setter
@Getter
public class BlacklistRule extends BaseJpaEntity {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "created_by")
  private Long createdBy;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "rule_id")
  private String ruleId;

  @Column(name = "rule_version")
  private String ruleVersion;

  @Column(name = "status")
  private String status;

  @Column(name = "days")
  private Integer days;

  @Column(name = "add_id_number", nullable = false, columnDefinition = "bool default false")
  private boolean addIdNumber;

  @Column(name = "add_phone", nullable = false, columnDefinition = "bool default false")
  private boolean addPhone;

  @Column(name = "add_bank_account", nullable = false, columnDefinition = "bool default false")
  private boolean addBankAccount;

  @Column(name = "add_card_number", nullable = false, columnDefinition = "bool default false")
  private boolean addCardNumber;

  @Transient
  public String getRuleName() {
    return days + StringUtils.SPACE + "(" + ruleId + ")";
  }

  @Override
  public String repositoryName() {
    return "blacklistRuleJpaRepository";
  }
}
