package asia.atmonline.myriskservice.data.storage.entity.property.converter;

import com.hes.cc.data.backoffice.property.PropertyTypeConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.StringUtils;

public class LocalDatePropertyTypeConverter implements PropertyTypeConverter<LocalDate> {

  private static final DateTimeFormatter DEFAULT_DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");

  private DateTimeFormatter dtf;

  public LocalDatePropertyTypeConverter() {
    this.dtf = DEFAULT_DTF;
  }

  @Override
  public LocalDate parse(String value) {
    return StringUtils.isBlank(value) ? null : LocalDate.parse(value, dtf);
  }

  @Override
  public String format(Object object) {
    return object == null ? null : dtf.format((LocalDate) object);
  }
}
