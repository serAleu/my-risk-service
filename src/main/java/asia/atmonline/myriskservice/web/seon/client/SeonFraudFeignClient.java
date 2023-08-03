package asia.atmonline.myriskservice.web.seon.client;

import asia.atmonline.myriskservice.web.seon.dto.FraudRequest;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers({"Content-Type: application/json; charset=UTF-8", "Cache-control: no-cache", "X-API-KEY: {key}"})
public interface SeonFraudFeignClient {

  @RequestLine("POST /SeonRestService/fraud-api/v2.0?timeout={timeout}")
  Object getSeonFraud(@Param("key") String key, FraudRequest fraudRequest, @Param("timeout") Integer timeout);

}
