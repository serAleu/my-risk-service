package asia.atmonline.myriskservice.producers.fin;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinalSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.final.producer.queue-name}")
  private String awsSqsFinalProducerQueueName;

  public FinalSqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, "my-risk-final-response-preprod");
  }
}