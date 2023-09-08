package asia.atmonline.myriskservice.data.storage.entity.property.impl;

import asia.atmonline.myriskservice.data.storage.entity.property.Property;
import asia.atmonline.myriskservice.enums.property.PropertyType;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "system_property",
    indexes = @Index(name = "idx_property_key", columnList = "property_key"), schema = "my-back")
public class SystemProperty extends Property {

  public SystemProperty() {
    super();
  }

  public SystemProperty(String key, PropertyType type, Object value) {
    super(key, type, value);
  }

}
