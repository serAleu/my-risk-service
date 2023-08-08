package asia.atmonline.myriskservice.producers.dedup3;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Dedup3SqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.dedup-3.producer.queue-name}")
  private String awsSqsDedup3ProducerQueueName;

  public Dedup3SqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, awsSqsDedup3ProducerQueueName);
  }
}