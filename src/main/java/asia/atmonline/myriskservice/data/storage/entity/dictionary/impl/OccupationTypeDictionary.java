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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
@Table(name = "dictionary_occupation_type", schema = "my-back")
@NoArgsConstructor
@Getter
@Setter
public class OccupationTypeDictionary extends BaseDictionary {

  @Column(name = "working_industries")
  @JdbcTypeCode(SqlTypes.JSON)
  private Set<Long> workingIndustries = new HashSet<>();
}