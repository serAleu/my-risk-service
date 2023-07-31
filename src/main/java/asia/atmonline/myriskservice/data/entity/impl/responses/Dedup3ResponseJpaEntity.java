package asia.atmonline.myriskservice.data.entity.impl.responses;

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
@Table(name = "dedup3_checks_response")
@SequenceGenerator(name = "sequence-generator", sequenceName = "dedup3_checks_response_id_seq", allocationSize = 1)
public class Dedup3ResponseJpaEntity extends BaseJpaEntity {

}
