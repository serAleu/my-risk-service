package asia.atmonline.myriskservice.producers.basic;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class BasicSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.basic.producer.queue-name}")
  private String awsSqsBasicProducerQueueName;

  public BasicSqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponse<? extends BaseSqsProducer> riskResponse) {
    super.sendResponseToQueue(riskResponse, awsSqsBasicProducerQueueName);
  }
}