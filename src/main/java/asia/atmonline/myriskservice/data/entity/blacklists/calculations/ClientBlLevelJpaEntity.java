package asia.atmonline.myriskservice.data.entity.blacklists.calculations;

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
@Table(name = "client_bl_level")
@SequenceGenerator(name = "sequence-generator", sequenceName = "client_bl_level_id_seq", allocationSize = 1)
public class ClientBlLevelJpaEntity extends BaseJpaEntity {

  @Column(name = "phone")
  private String phone;
  @Column(name = "bl_level")
  private Integer blLevel;
  @Column(name = "borrower_id")
  private Long borrowerId;

  @Override
  public String repositoryName() {
    return "getClientBlLevelJpaRepository";
  }
}
