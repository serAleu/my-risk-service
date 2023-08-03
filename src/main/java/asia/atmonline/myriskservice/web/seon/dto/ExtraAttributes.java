package asia.atmonline.myriskservice.web.seon.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.collections4.MapUtils;


@Getter
@AllArgsConstructor
public enum ExtraAttributes {
  SEON_SESSION_ID (true),
  SEON_SESSION(true),
  PUSH_NOTIFICATION_ELIGIBILITY(true),
  ;

  private final boolean updatable;

  public static Map<ExtraAttributes, String> mergeAttributes(Map<ExtraAttributes, String> attributesOld,
                                                             Map<ExtraAttributes, String> attributesNew) {
    if (MapUtils.isEmpty(attributesOld) && MapUtils.isEmpty(attributesNew)) {
      return new HashMap<>();
    } else if (MapUtils.isEmpty(attributesOld)) {
      return attributesNew;
    } else if (MapUtils.isEmpty(attributesNew)) {
      return attributesOld;
    }

    Map<ExtraAttributes, String> result = new HashMap<>(attributesOld);
    attributesOld.forEach((attribute, value) -> {
      if (attribute.isUpdatable()) {
        result.remove(attribute);
      }
    });

    attributesNew.forEach((attribute, value) -> {
      if (!result.containsKey(attribute)) {
        result.put(attribute, value);
      }
    });

    return result;
  }
}
