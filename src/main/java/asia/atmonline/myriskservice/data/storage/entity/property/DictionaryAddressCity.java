package asia.atmonline.myriskservice.data.storage.entity.property;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dictionary_address_city")
@NoArgsConstructor
@Getter
@Setter
public class DictionaryAddressCity extends BaseStorageEntity {


  @Column(name = "deleted")
  private Boolean deleted;
  @Column(name = "active")
  private Boolean active;
  @Column(name = "nameen")
  private String nameEn;
  @Column(name = "namemy")
  private String nameMy;
  @Column(name = "prohibited")
  private Boolean prohibited;
  @Column(name = "state_id")
  private Long stateId;
}