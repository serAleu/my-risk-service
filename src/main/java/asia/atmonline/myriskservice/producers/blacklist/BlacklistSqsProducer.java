package asia.atmonline.myriskservice.producers.blacklist;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class BlacklistSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.blacklists.producer.queue-name}")
  private String awsSqsBlacklistsProducerQueueName;

  public BlacklistSqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, "my-risk-bl-response-preprod");
  }
}