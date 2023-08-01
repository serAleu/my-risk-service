package asia.atmonline.myriskservice.data.entity.impl.responses;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseResponseJpaEntity {

  private Long requestId;

}
