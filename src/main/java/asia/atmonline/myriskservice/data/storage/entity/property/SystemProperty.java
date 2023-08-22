package asia.atmonline.myriskservice.data.storage.entity.property;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "system_property")
@NoArgsConstructor
@Getter
@Setter
public class SystemProperty extends BaseStorageEntity {

  @Column(name = "amount", nullable = false)
  private Long amount;
  @Column(name = "term", nullable = false)
  private Long term;
  @Column(name = "property_key", nullable = false)
  private Integer propertyKey;

}
