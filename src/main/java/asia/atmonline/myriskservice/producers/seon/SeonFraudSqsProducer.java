package asia.atmonline.myriskservice.producers.seon;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeonFraudSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.seon-fraud.producer.queue-name}")
  private String awsSqsSeonDataProducerQueueName;

  public SeonFraudSqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, awsSqsSeonDataProducerQueueName);
  }
}