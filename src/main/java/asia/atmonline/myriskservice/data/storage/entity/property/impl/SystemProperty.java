package asia.atmonline.myriskservice.data.storage.entity.property.impl;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import asia.atmonline.myriskservice.enums.property.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "system_property",schema = "my-back")
@NoArgsConstructor
@Getter
@Setter
public class SystemProperty extends BaseStorageEntity {

  @Column(name = "property_key")
  private String propertyKey;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private PropertyType type;

  @Column(name = "value", columnDefinition = "text")
  private String value;

}
