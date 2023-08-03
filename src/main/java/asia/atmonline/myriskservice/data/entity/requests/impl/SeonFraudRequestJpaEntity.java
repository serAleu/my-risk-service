package asia.atmonline.myriskservice.data.entity.requests.impl;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
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
@Table(name = "seon_data_request")
@SequenceGenerator(name = "sequence-generator", sequenceName = "seon_data_request_id_seq", allocationSize = 1)
public class SeonFraudRequestJpaEntity extends BaseJpaEntity {

  @Override
  public String repositoryName() {
    return "seonDataRequestJpaRepository";
  }
}
