package asia.atmonline.myriskservice.enums.property;

import asia.atmonline.myriskservice.data.storage.entity.property.converter.BooleanPropertyTypeConverter;
import asia.atmonline.myriskservice.data.storage.entity.property.converter.DecimalPropertyTypeConverter;
import asia.atmonline.myriskservice.data.storage.entity.property.converter.IntegerPropertyTypeConverter;
import asia.atmonline.myriskservice.data.storage.entity.property.converter.LocalDatePropertyTypeConverter;
import asia.atmonline.myriskservice.data.storage.entity.property.converter.LocalDateTimeTypeConverter;
import asia.atmonline.myriskservice.data.storage.entity.property.converter.LongPropertyTypeConverter;
import asia.atmonline.myriskservice.data.storage.entity.property.converter.PropertyTypeConverter;
import asia.atmonline.myriskservice.data.storage.entity.property.converter.StringPropertyTypeConverter;

public enum PropertyType {

  STRING(new StringPropertyTypeConverter()),
  LONG(new LongPropertyTypeConverter()),
  DECIMAL(new DecimalPropertyTypeConverter()),
  BOOLEAN(new BooleanPropertyTypeConverter()),
  INTEGER(new IntegerPropertyTypeConverter()),
  LOCAL_DATE_TIME(new LocalDateTimeTypeConverter()),
  LOCAL_DATE(new LocalDatePropertyTypeConverter());
  // Добавлять новые в конец!!!

  private PropertyTypeConverter converter;

  PropertyType(PropertyTypeConverter converter) {
    this.converter = converter;
  }

  public PropertyTypeConverter getConverter() {
    return converter;
  }
}
