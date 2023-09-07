package asia.atmonline.myriskservice.data.storage.entity.property.converter;

public interface PropertyTypeConverter<T> {

  T parse(String value);

  String format(Object object);
}
