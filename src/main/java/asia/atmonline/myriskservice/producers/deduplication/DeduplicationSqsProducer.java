package asia.atmonline.myriskservice.producers.deduplication;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.dedup.producer.queue-name}")
  private String awsSqsDedupProducerQueueName;

  public DeduplicationSqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, awsSqsDedupProducerQueueName);
  }
}