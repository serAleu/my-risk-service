package asia.atmonline.myriskservice.data.storage.entity.property.converter;

public class BooleanPropertyTypeConverter implements PropertyTypeConverter {

  @Override
  public Object parse(String value) {
    return value != null ? Boolean.parseBoolean(value) : null;
  }

  @Override
  public String format(Object object) {
    return object != null ? Boolean.toString((Boolean) object) : null;
  }

}
