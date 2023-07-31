package asia.atmonline.myriskservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Getter
@EnableSqs
public class AwsConfig {

  @Value("${aws.credentials.access-key}")
  private String awsCredentialsAccessKey;
  @Value("${aws.credentials.secret-key}")
  private String awsCredentialsSecretKey;
  @Value("${aws.default-task-executor.thread-name-prefix}")
  private String awsDefaultTaskExecutorThreadNamePrefix;
  @Value("${aws.default-task-executor.core-pool-size}")
  private Integer awsDefaultTaskExecutorCorePoolSize;
  @Value("${aws.default-task-executor.max-pool-size}")
  private Integer awsDefaultTaskExecutorMaxPoolSize;
  @Value("${aws.default-task-executor.queue-capacity}")
  private Integer awsDefaultTaskExecutorQueueCapacity;
  @Value("{$aws.default-task-executor.blocking-task-timeout}")
  private Integer awsDefaultTaskExecutorBlockingTaskTimeout;
  @Value("${aws.sqs.max-number-of-messages}")
  private Integer awsSqsMaxNumberOfMessages;
  @Value("{$aws.sqs.back-off-time}")
  private Long awsSqsBackOffTime;
  @Value("{$aws.sqs.auto-startup}")
  private Boolean awsSqsAutoStartup;

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
    return new BasicAWSCredentials(awsCredentialsAccessKey,awsCredentialsSecretKey);
  }

  @Bean
  public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory() {
    SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
    factory.setAmazonSqs(awsSQSAsync());
    factory.setAutoStartup(awsSqsAutoStartup);
    factory.setMaxNumberOfMessages(awsSqsMaxNumberOfMessages);
    factory.setBackOffTime(awsSqsBackOffTime);
    return factory;
  }

  @Bean
  public SimpleMessageListenerContainer simpleMessageListenerContainer() {
    SimpleMessageListenerContainer messageListenerContainer = simpleMessageListenerContainerFactory().createSimpleMessageListenerContainer();
    messageListenerContainer.setMessageHandler(queueMessageHandler());
    return messageListenerContainer;
  }

  @Bean
  public QueueMessagingTemplate queueMessagingTemplate() {
    return new QueueMessagingTemplate(awsSQSAsync());
  }

  @Bean(name = "threadPoolQueue")
  public AsyncTaskExecutor createDefaultTaskExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setThreadNamePrefix(awsDefaultTaskExecutorThreadNamePrefix);
    threadPoolTaskExecutor.setCorePoolSize(awsDefaultTaskExecutorCorePoolSize);
    threadPoolTaskExecutor.setMaxPoolSize(awsDefaultTaskExecutorMaxPoolSize);
    threadPoolTaskExecutor.setQueueCapacity(awsDefaultTaskExecutorQueueCapacity);
    threadPoolTaskExecutor.afterPropertiesSet();
    threadPoolTaskExecutor.setRejectedExecutionHandler(new BlockingTaskSubmissionPolicy(awsDefaultTaskExecutorBlockingTaskTimeout));
    return threadPoolTaskExecutor;
  }

  @Bean
  public QueueMessageHandler queueMessageHandler() {
    QueueMessageHandlerFactory queueMsgHandlerFactory = new QueueMessageHandlerFactory();
    queueMsgHandlerFactory.setAmazonSqs(awsSQSAsync());
    QueueMessageHandler queueMessageHandler = queueMsgHandlerFactory.createQueueMessageHandler();
    List<HandlerMethodArgumentResolver> list = new ArrayList<>();
    HandlerMethodArgumentResolver resolver = new PayloadMethodArgumentResolver(new MappingJackson2MessageConverter());
    list.add(resolver);
    queueMessageHandler.setArgumentResolvers(list);
    return queueMessageHandler;
  }
}