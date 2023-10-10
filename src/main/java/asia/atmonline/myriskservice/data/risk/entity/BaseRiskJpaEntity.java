package asia.atmonline.myriskservice.data.risk.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public abstract class BaseRiskJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public abstract String repositoryName();
}
