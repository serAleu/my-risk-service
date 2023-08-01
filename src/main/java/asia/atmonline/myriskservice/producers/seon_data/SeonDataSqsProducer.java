package asia.atmonline.myriskservice.producers.seon_data;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeonDataSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.seon-data.producer.queue-name}")
  private String awsSqsSeonDataProducerQueueName;

  public SeonDataSqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponse<? extends BaseSqsProducer> riskResponse) {
    super.sendResponseToQueue(riskResponse, awsSqsSeonDataProducerQueueName);
  }
}