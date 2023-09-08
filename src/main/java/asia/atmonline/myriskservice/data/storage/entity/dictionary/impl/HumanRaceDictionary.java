package asia.atmonline.myriskservice.data.storage.entity.dictionary.impl;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.BaseDictionary;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dictionary_human_race", schema = "my-back")
@NoArgsConstructor
@Getter
@Setter
public class HumanRaceDictionary extends BaseDictionary {

}
