package asia.atmonline.myriskservice.data.entity.blacklists.entity;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BlacklistBaseJpaEntity extends BaseJpaEntity {

}
