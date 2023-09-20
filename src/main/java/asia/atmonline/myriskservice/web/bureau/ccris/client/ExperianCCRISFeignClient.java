package asia.atmonline.myriskservice.web.bureau.ccris.client;

import feign.Headers;
import feign.RequestLine;
import org.springframework.http.MediaType;

public interface ExperianCCRISFeignClient {

  @RequestLine("POST /report")
  @Headers({
      "Content-Type: " + MediaType.APPLICATION_JSON_VALUE,
      "Authorization: {authorization}"
  })
  String getCCRISInfo(String request);

}
