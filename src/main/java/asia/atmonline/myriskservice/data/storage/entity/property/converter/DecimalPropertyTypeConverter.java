package asia.atmonline.myriskservice.data.storage.entity.property.converter;

import com.hes.cc.data.backoffice.property.PropertyTypeConverter;
import java.math.BigDecimal;

public class DecimalPropertyTypeConverter implements PropertyTypeConverter {

  @Override
  public Object parse(String value) {
    return value != null ? new BigDecimal(value) : null;
  }

  @Override
  public String format(Object object) {
    return object != null ? ((BigDecimal) object).toString() : null;
  }

}
