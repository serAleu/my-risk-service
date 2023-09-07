package asia.atmonline.myriskservice.data.storage.entity.property.converter;

public class LongPropertyTypeConverter implements PropertyTypeConverter {

  @Override
  public Object parse(String value) {
    return value != null ? Long.parseLong(value) : null;
  }

  @Override
  public String format(Object object) {
    return object != null ? Long.toString((Long) object) : null;
  }

}
