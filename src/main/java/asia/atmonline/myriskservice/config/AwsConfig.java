package asia.atmonline.myriskservice.config;

import asia.atmonline.myriskservice.consumer.payload.RequestPayload;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.config.SqsBootstrapConfiguration;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.acknowledgement.AcknowledgementOrdering;
import io.awspring.cloud.sqs.listener.acknowledgement.handler.AcknowledgementMode;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.operations.TemplateAcknowledgementMode;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import java.time.Duration;
import org.springframework.cloud.aws.core.env.ResourceIdResolver;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Import(SqsBootstrapConfiguration.class)
@Configuration
public class AwsConfig {

  @Bean
  public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync, MessageConverter messageConverter) {
    return new QueueMessagingTemplate(amazonSQSAsync, (ResourceIdResolver) null, messageConverter);
  }

  @Bean
  @Primary
  public SqsAsyncClient sqsAsyncClient() {
    return SqsAsyncClient.builder()
        .region(Region.AP_SOUTHEAST_1)
        .build();
  }

  @Bean
  @Primary
  public AmazonSQSAsync awsSQSAsync() {
    return AmazonSQSAsyncClientBuilder.standard()
        .withRegion(Regions.AP_SOUTHEAST_1)
        .build();
  }

  @Bean
  public SqsMessagingMessageConverter getSqsMessagingMessageConverter(ObjectMapper mapper) {
    SqsMessagingMessageConverter messageConverter = new SqsMessagingMessageConverter();
    MappingJackson2MessageConverter payloadConverter = new MappingJackson2MessageConverter();
    payloadConverter.setPrettyPrint(true);
    payloadConverter.setObjectMapper(mapper);
    messageConverter.setPayloadMessageConverter(payloadConverter);
    return messageConverter;
  }

  @Bean
  public SqsTemplate template(SqsAsyncClient sqsAsyncClient, ObjectMapper mapper) {
    return SqsTemplate.builder()
        .configureDefaultConverter(converter -> {
              converter.setObjectMapper(mapper);
            }
        )
        .sqsAsyncClient(sqsAsyncClient)
        .configure(options -> options
            .acknowledgementMode(TemplateAcknowledgementMode.MANUAL)
            .defaultPayloadClass(RequestPayload.class)
        )
        .build();
  }

  @Bean
  SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient,
      SqsMessagingMessageConverter messageConverter) {
    return SqsMessageListenerContainerFactory
        .builder()
        .configure(options -> options
            .acknowledgementMode(AcknowledgementMode.ON_SUCCESS)
            .acknowledgementInterval(Duration.ofSeconds(3))
            .acknowledgementThreshold(5)
            .acknowledgementOrdering(AcknowledgementOrdering.ORDERED)
            .messageConverter(messageConverter)
        )
        .sqsAsyncClient(sqsAsyncClient)
        .build();
  }

  @Bean
  protected MessageConverter messageConverter(ObjectMapper objectMapper) {

    var converter = new MappingJackson2MessageConverter();
    converter.setObjectMapper(objectMapper);
    // Serialization support:
    converter.setSerializedPayloadClass(String.class);
    // Deserialization support: (suppress "contentType=application/json" header requirement)
    converter.setStrictContentTypeMatch(false);
    return converter;
  }
}