package asia.atmonline.myriskservice.producers.fin;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
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
  public void sendResponse(RiskResponse<? extends BaseSqsProducer> riskResponse) {
    super.sendResponseToQueue(riskResponse, awsSqsFinalProducerQueueName);
  }
}