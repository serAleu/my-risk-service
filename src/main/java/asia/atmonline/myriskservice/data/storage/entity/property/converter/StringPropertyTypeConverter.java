package asia.atmonline.myriskservice.data.storage.entity.property.converter;

public class StringPropertyTypeConverter implements PropertyTypeConverter {

  @Override
  public Object parse(String value) {
    return value;
  }

  @Override
  public String format(Object object) {
    return object != null ? (String) object : null;
  }

}
