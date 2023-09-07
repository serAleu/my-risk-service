package asia.atmonline.myriskservice.data.risk.entity.blacklists.impl;

import asia.atmonline.myriskservice.data.risk.entity.blacklists.BlacklistBaseRiskJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blacklist_passport_number")
@Getter
@Setter
@AllArgsConstructor
public class BlacklistPassportNumberRiskJpaEntity extends BlacklistBaseRiskJpaEntity {

  @Column(name = "passport_number")
  private String passportNumber;

  @Override
  public String repositoryName() {
    return "blacklistIdNumberJpaRepository";
  }
}
