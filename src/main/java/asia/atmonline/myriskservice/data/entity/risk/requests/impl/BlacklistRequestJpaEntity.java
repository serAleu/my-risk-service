package asia.atmonline.myriskservice.data.entity.risk.requests.impl;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@Entity
@Table(name = "blacklist_checks_request")
@SequenceGenerator(name = "sequence-generator", sequenceName = "blacklist_checks_request_id_seq", allocationSize = 1)
public class BlacklistRequestJpaEntity extends BaseJpaEntity {

  @Column(name = "phone_num", nullable = false)
  private String phoneNum;

  @Override
  public String repositoryName() {
    return "blacklistRequestJpaRepository";
  }
}
