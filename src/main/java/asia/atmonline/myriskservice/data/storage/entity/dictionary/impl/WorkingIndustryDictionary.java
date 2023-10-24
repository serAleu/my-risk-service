package asia.atmonline.myriskservice.data.storage.entity.dictionary.impl;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.BaseDictionary;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dictionary_working_industry", schema = "my-back")
@NoArgsConstructor
@Getter
@Setter
public class WorkingIndustryDictionary extends BaseDictionary {

  @Column(name = "prohibited", nullable = false)
  private boolean prohibited;
}