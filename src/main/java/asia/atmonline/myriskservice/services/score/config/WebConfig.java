package asia.atmonline.myriskservice.services.score.config;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

  @Value("${score.bitbucket.token}")
  private String bitbucketBasicToken;

  @Bean(name = "restTemplate")
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters()
        .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    return restTemplate;
  }

  @Bean(name = "httpEntity")
  public HttpEntity<?> httpEntity() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", bitbucketBasicToken);
    return new HttpEntity<>(headers);
  }
}
