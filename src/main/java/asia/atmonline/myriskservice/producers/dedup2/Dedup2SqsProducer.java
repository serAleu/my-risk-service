package asia.atmonline.myriskservice.producers.dedup2;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Dedup2SqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.dedup-2.producer.queue-name}")
  private String awsSqsDedup2ProducerQueueName;

  public Dedup2SqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponse<? extends BaseSqsProducer> riskResponse) {
    super.sendResponseToQueue(riskResponse, awsSqsDedup2ProducerQueueName);
  }
}