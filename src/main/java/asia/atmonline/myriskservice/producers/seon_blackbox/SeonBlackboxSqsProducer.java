package asia.atmonline.myriskservice.producers.seon_blackbox;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeonBlackboxSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.seon-blackbox.producer.queue-name}")
  private String awsSqsSeonBlackboxProducerQueueName;

  public SeonBlackboxSqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponse<? extends BaseSqsProducer> riskResponse) {
    super.sendResponseToQueue(riskResponse, awsSqsSeonBlackboxProducerQueueName);
  }
}