package asia.atmonline.myriskservice.config.seon;

import static feign.Logger.Level.FULL;
import static java.util.concurrent.TimeUnit.SECONDS;

import asia.atmonline.myriskservice.services.seon.property.SeonPropertyManager;
import asia.atmonline.myriskservice.web.seon.client.SeonFraudFeignClient;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SeonConfig {

  private final SeonPropertyManager manager;

  @Bean
  public SeonFraudFeignClient seonRemoteService() {
    return Feign.builder()
        .encoder(new JacksonEncoder())
        .decoder(new JacksonDecoder())
        .options(new Request.Options(3 * 1000, 30 * 1000))
        .retryer(new Retryer.Default(100, SECONDS.toMillis(1), 1))
        .logLevel(FULL)
        .target(SeonFraudFeignClient.class, manager.getSeonFraudBaseUrl());
  }
}
