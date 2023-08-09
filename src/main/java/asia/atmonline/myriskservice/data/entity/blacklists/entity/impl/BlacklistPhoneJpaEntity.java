package asia.atmonline.myriskservice.data.entity.blacklists.entity.impl;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.BlacklistBaseJpaEntity;
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
public class BlacklistPhoneJpaEntity extends BlacklistBaseJpaEntity {

  @Column(name = "phone")
  private String phone;

  @Override
  public String repositoryName() {
    return "blacklistPhoneJpaRepository";
  }
}
