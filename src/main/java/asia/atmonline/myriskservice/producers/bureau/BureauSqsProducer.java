package asia.atmonline.myriskservice.producers.bureau;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class BureauSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.bureau.producer.queue-name}")
  private String awsSqsBureauProducerQueueName;

  public BureauSqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponse<? extends BaseSqsProducer> riskResponse) {
    super.sendResponseToQueue(riskResponse, awsSqsBureauProducerQueueName);
  }
}