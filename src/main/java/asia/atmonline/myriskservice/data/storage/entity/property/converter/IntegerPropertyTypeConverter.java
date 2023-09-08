package asia.atmonline.myriskservice.data.storage.entity.property.converter;

public class IntegerPropertyTypeConverter implements PropertyTypeConverter {

  @Override
  public Object parse(String value) {
    return value != null ? Integer.parseInt(value) : null;
  }

  @Override
  public String format(Object object) {
    return object != null ? Integer.toString((Integer) object) : null;
  }

}
