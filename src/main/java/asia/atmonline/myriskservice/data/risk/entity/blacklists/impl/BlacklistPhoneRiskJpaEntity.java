package asia.atmonline.myriskservice.data.risk.entity.blacklists.impl;

import asia.atmonline.myriskservice.data.risk.entity.blacklists.BlacklistBaseRiskJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blacklist_phone")
@Getter
@Setter
@AllArgsConstructor
public class BlacklistPhoneRiskJpaEntity extends BlacklistBaseRiskJpaEntity {

  @Column(name = "phone")
  private String phone;

  @Override
  public String repositoryName() {
    return "blacklistPhoneJpaRepository";
  }
}
