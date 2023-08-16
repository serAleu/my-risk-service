package asia.atmonline.myriskservice.producers.cooldown;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class CooldownSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.cooldown.producer.queue-name}")
  private String awsSqsDedup2ProducerQueueName;

  public CooldownSqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, awsSqsDedup2ProducerQueueName);
  }
}