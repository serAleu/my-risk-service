package asia.atmonline.myriskservice.data.storage.entity.property.converter;

import com.hes.cc.data.backoffice.property.PropertyTypeConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author Ivan Litvinov
 */
public class LocalDateTimeTypeConverter implements PropertyTypeConverter {

  public final static DateTimeFormatter DEFAULT_DTF = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  private DateTimeFormatter dtf;

  public LocalDateTimeTypeConverter() {
    dtf = DEFAULT_DTF;
  }

  public LocalDateTimeTypeConverter(DateTimeFormatter dtf) {
    Objects.requireNonNull(dtf);
    this.dtf = dtf;
  }

  @Override
  public Object parse(String value) {
    return value == null ? null : LocalDateTime.parse(value, dtf);
  }

  @Override
  public String format(Object object) {
    return object == null ? null : dtf.format((LocalDateTime) object);
  }
}
