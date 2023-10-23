package asia.atmonline.myriskservice.data.risk.entity.blacklists;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "blacklist_rule")
@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
public class BlacklistRule {

  @Id
  @Column(name = "id")
  private String id;

  @Column(name = "created_by")
  private Long createdBy;

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

  public String repositoryName() {
    return "blacklistRuleJpaRepository";
  }
}
