package asia.atmonline.myriskservice.data.entity.responses.impl;

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
@Table(name = "basic_checks_response")
@SequenceGenerator(name = "sequence-generator", sequenceName = "basic_checks_response_id_seq", allocationSize = 1)
public class BasicResponseJpaEntity extends BaseJpaEntity {

  @Override
  public String repositoryName() {
    return "basicResponseJpaRepository";
  }
}