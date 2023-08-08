package asia.atmonline.myriskservice.data.entity.risk.responses;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseResponseJpaEntity extends BaseJpaEntity {

  private Long requestId;

}
