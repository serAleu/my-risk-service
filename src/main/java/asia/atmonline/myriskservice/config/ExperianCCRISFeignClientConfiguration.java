package asia.atmonline.myriskservice.config;

import static java.util.concurrent.TimeUnit.SECONDS;

import asia.atmonline.myriskservice.web.bureau.experian.client.ExperianCCRISFeignClient;
import feign.Feign;
import feign.Request;
import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExperianCCRISFeignClientConfiguration {

  @Value("${experian.feign.options.connect-timeout-millis}")
  private Integer feignOptionsConnectTimeoutMillis;
  @Value("${experian.feign.options.read-timeout-millis}")
  private Integer feignOptionsReadTimeoutMillis;
  @Value("${experian.feign.retryer.period}")
  private Integer feignRetryerPeriod;
  @Value("${experian.feign.retryer.duration}")
  private Integer feignRetryerDuration;
  @Value("${experian.feign.retryer.max-attempts}")
  private Integer feignRetryerMaxAttempts;
  @Value("${experian.base-url}")
  private String experianBaseUrl;
  @Value("${experian.username}")
  private String experianBasicUsername;
  @Value("${experian.password}")
  private String experianBasicPassword;

//  @Bean
//  public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
//    return new BasicAuthRequestInterceptor(experianBasicUsername, experianBasicPassword);
//  }

  @Bean
  public ExperianCCRISFeignClient experianCCRISFeignClient() {
    return Feign.builder()
//        .encoder(new JacksonEncoder())
//        .decoder(new JacksonDecoder())
        .requestInterceptor(new BasicAuthRequestInterceptor(experianBasicUsername, experianBasicPassword))
        .options(new Request.Options(Duration.ofSeconds(feignOptionsConnectTimeoutMillis),
            Duration.ofSeconds(feignOptionsReadTimeoutMillis), true))
        .retryer(new Retryer.Default(feignRetryerPeriod, SECONDS.toMillis(feignRetryerDuration), feignRetryerMaxAttempts))
        .target(ExperianCCRISFeignClient.class, experianBaseUrl);
  }
}