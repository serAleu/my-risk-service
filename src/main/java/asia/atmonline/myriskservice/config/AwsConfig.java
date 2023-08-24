package asia.atmonline.myriskservice.config;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import io.awspring.cloud.autoconfigure.sqs.SqsProperties.Listener;
import io.awspring.cloud.sqs.config.SqsBootstrapConfiguration;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementOrdering;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.operations.TemplateAcknowledgementMode;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import java.time.Duration;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Import(SqsBootstrapConfiguration.class)
@Configuration
public class AwsConfig {

  @NotNull
  private static SqsMessagingMessageConverter getSqsMessagingMessageConverter() {
    SqsMessagingMessageConverter messageConverter = new SqsMessagingMessageConverter();
    MappingJackson2MessageConverter payloadConverter = new MappingJackson2MessageConverter();
    payloadConverter.setPrettyPrint(true);
    messageConverter.setPayloadMessageConverter(payloadConverter);
    return messageConverter;
  }

  @Bean
  public SqsTemplate template(SqsAsyncClient sqsAsyncClient) {
    return SqsTemplate.builder()
        .sqsAsyncClient(sqsAsyncClient)
        .configure(options -> options
            .acknowledgementMode(TemplateAcknowledgementMode.MANUAL)
            .defaultPayloadClass(RiskResponseJpaEntity.class)
        )
        .build();
  }

  @Bean
  public Listener listener() {
    return new Listener();
  }

  @Bean
  SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
    return SqsMessageListenerContainerFactory
        .builder()
        .configure(options -> options
            .acknowledgementMode(AcknowledgementMode.ALWAYS)
            .acknowledgementInterval(Duration.ofSeconds(3))
            .acknowledgementThreshold(5)
            .acknowledgementOrdering(AcknowledgementOrdering.ORDERED)
            .messageConverter(getSqsMessagingMessageConverter())
        )
        .sqsAsyncClient(sqsAsyncClient)
        .build();
  }


}