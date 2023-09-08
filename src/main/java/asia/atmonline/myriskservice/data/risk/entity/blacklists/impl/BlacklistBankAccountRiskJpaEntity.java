package asia.atmonline.myriskservice.data.risk.entity.blacklists.impl;

import asia.atmonline.myriskservice.data.risk.entity.blacklists.BlacklistBaseRiskJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "blacklist_bank_account")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BlacklistBankAccountRiskJpaEntity extends BlacklistBaseRiskJpaEntity {

  @Column(name = "bank_account")
  private String bankAccount;

  @Override
  public String repositoryName() {
    return "blacklistBankAccountJpaRepository";
  }
}
