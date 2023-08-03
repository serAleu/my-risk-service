package asia.atmonline.myriskservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonUtils {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private final ObjectMapper objectMapper;

  @SneakyThrows
  public String encode(Object object) {
    return objectMapper.writeValueAsString(object);
  }

  @SneakyThrows
  public static String encodeDefault(Object object) {
    return MAPPER.writeValueAsString(object);
  }

  @SneakyThrows
  public <T> T decode(String json, Class<T> clazz) {
    return objectMapper.readValue(json, clazz);
  }

  @SneakyThrows
  public static <T> T decodeDefault(String json, Class<T> clazz) {
    return MAPPER.readValue(json, clazz);
  }

  public static Map readToMap(String jsonString) throws IOException {
    return MAPPER.readValue(jsonString, Map.class);
  }

  public static <T> T convertValue(Object fromValue, Class<T> clazz) {
    return MAPPER.convertValue(fromValue, clazz);
  }

}