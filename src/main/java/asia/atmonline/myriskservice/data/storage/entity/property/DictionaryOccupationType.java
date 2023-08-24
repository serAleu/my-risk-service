package asia.atmonline.myriskservice.data.storage.entity.property;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dictionary_occupation_type")
@NoArgsConstructor
@Getter
@Setter
public class DictionaryOccupationType extends BaseStorageEntity {

  @Column(name = "deleted")
  private Boolean deleted;
  @Column(name = "active")
  private Boolean active;
  @Column(name = "nameen")
  private String nameEn;
  @Column(name = "namemy")
  private String nameMy;
  @Column(name = "working_industries")
  private List<Integer> workingIndustries;
}