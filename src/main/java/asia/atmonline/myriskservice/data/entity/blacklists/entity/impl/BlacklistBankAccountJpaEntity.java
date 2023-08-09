package asia.atmonline.myriskservice.data.entity.blacklists.entity.impl;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.BlacklistBaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blacklist_bank_account")
@Getter
@Setter
@AllArgsConstructor
public class BlacklistBankAccountJpaEntity extends BlacklistBaseJpaEntity {

  @Column(name = "bank_account")
  private String bankAccount;

  @Override
  public String repositoryName() {
    return "blacklistBankAccountJpaRepository";
  }
}
