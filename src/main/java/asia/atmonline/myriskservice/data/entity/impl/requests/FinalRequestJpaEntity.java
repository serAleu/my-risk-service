package asia.atmonline.myriskservice.data.entity.impl.requests;

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
@Table(name = "final_checks_request")
@SequenceGenerator(name = "sequence-generator", sequenceName = "final_checks_request_id_seq", allocationSize = 1)
public class FinalRequestJpaEntity extends BaseJpaEntity {

}
