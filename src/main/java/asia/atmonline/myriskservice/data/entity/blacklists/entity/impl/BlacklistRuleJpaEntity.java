package asia.atmonline.myriskservice.data.entity.blacklists.entity.impl;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.BlacklistBaseJpaEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "blacklist_rule")
@Setter
@Getter
public class BlacklistRuleJpaEntity extends BlacklistBaseJpaEntity {

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
    return "blacklistPhoneJpaRepository";
  }
}
