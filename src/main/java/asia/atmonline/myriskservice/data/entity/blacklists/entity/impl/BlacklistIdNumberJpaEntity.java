package asia.atmonline.myriskservice.data.entity.blacklists.entity.impl;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.BlacklistBaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "blacklist_id_number")
@Getter
@Setter
@AllArgsConstructor
public class BlacklistIdNumberJpaEntity extends BlacklistBaseJpaEntity {

  @Column(name = "id_number")
  private String idNumber;

  @Override
  public String repositoryName() {
    return "blacklistIdNumberJpaRepository";
  }
}
