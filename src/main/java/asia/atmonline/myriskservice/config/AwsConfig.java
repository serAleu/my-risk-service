package asia.atmonline.myriskservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.core.env.ResourceIdResolver;
import io.awspring.cloud.messaging.config.QueueMessageHandlerFactory;
import io.awspring.cloud.messaging.config.annotation.EnableSqs;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.QueueMessageHandler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
//@Getter
public class AwsConfig {

  @Value("${aws.credentials.access-key}")
  private String awsCredentialsAccessKey;
  @Value("${aws.credentials.secret-key}")
  private String awsCredentialsSecretKey;
  @Value("${aws.default-task-executor.core-pool-size}")
  private Integer awsDefaultTaskExecutorCorePoolSize;
  @Value("${aws.default-task-executor.max-pool-size}")
  private Integer awsDefaultTaskExecutorMaxPoolSize;
  @Value("${aws.default-task-executor.queue-capacity}")
  private Integer awsDefaultTaskExecutorQueueCapacity;
  //  @Value("{$aws.default-task-executor.blocking-task-timeout}")
//  private Long awsDefaultTaskExecutorBlockingTaskTimeout;
  @Value("${aws.sqs.max-number-of-messages}")
  private Integer awsSqsMaxNumberOfMessages;
//  @Value("{$aws.sqs.back-off-time}")
//  private String awsSqsBackOffTime;

  @Bean
  @Primary
  public AmazonSQSAsync awsSQSAsync() {
    return AmazonSQSAsyncClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentialsProvider()))
        .withRegion(Regions.AP_SOUTHEAST_1)
        .build();
  }

  @Bean
  public BasicAWSCredentials awsCredentialsProvider() {
    return new BasicAWSCredentials(awsCredentialsAccessKey, awsCredentialsSecretKey);
  }

  @Bean
  public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync, MessageConverter messageConverter) {
    return new QueueMessagingTemplate(amazonSQSAsync, (ResourceIdResolver) null, messageConverter);
  }

  @Bean(name = "threadPoolQueue")
  public AsyncTaskExecutor createDefaultTaskExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setThreadNamePrefix("SQSExecutor - ");
    threadPoolTaskExecutor.setCorePoolSize(awsDefaultTaskExecutorCorePoolSize);
    threadPoolTaskExecutor.setMaxPoolSize(awsDefaultTaskExecutorMaxPoolSize);
    threadPoolTaskExecutor.setQueueCapacity(awsDefaultTaskExecutorQueueCapacity);
    threadPoolTaskExecutor.afterPropertiesSet();
    threadPoolTaskExecutor.setRejectedExecutionHandler(new BlockingTaskSubmissionPolicy(1000));
    return threadPoolTaskExecutor;
  }

  @Bean
  public QueueMessageHandlerFactory queueMessageHandlerFactory(
      final AmazonSQSAsync amazonSQSAsync,
      final MessageConverter messageConverter,
      final ObjectMapper mapper) {

    final QueueMessageHandlerFactory queueHandlerFactory = new QueueMessageHandlerFactory();
    queueHandlerFactory.setAmazonSqs(amazonSQSAsync);
    queueHandlerFactory.setArgumentResolvers(Collections.singletonList(new PayloadMethodArgumentResolver(messageConverter)));
    queueHandlerFactory.setObjectMapper(mapper);
    queueHandlerFactory.setMessageConverters(Collections.singletonList(messageConverter));
    return queueHandlerFactory;
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