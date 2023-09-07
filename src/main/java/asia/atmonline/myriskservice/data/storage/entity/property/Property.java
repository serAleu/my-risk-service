package asia.atmonline.myriskservice.data.storage.entity.property;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import asia.atmonline.myriskservice.enums.property.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public abstract class Property extends BaseStorageEntity {

  @Column(name = "property_key")
  private String key;

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private PropertyType type;

  @Column(name = "value", columnDefinition = "text")
  private String value;

  public Property() {
  }

  public Property(String key, PropertyType type, Object value) {
    super();
    this.key = key;
    this.type = type;
    this.value = type.getConverter().format(value);
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public PropertyType getType() {
    return type;
  }

  public void setType(PropertyType type) {
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = type.getConverter().format(value);
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Transient
  @SuppressWarnings("unchecked")
  public <T> T getParsedValue() {
    return (T) type.getConverter().parse(value);
  }

  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    Property other = (Property) obj;
    if (key == null) {
      if (other.key != null) {
        return false;
      }
    } else if (!key.equals(other.key)) {
      return false;
    }
    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }
}
