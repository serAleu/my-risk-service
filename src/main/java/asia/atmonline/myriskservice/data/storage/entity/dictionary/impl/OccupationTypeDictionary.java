package asia.atmonline.myriskservice.data.storage.entity.dictionary.impl;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.BaseDictionary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dictionary_occupation_type")
@NoArgsConstructor
@Getter
@Setter
public class OccupationTypeDictionary extends BaseDictionary {

  @Column(name = "working_industries")
  private Set<Long> workingIndustries = new HashSet<>();
}