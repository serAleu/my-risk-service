package asia.atmonline.myriskservice.producers.seon_blackbox;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeonBlackboxSqsProducer {

  private final QueueMessagingTemplate queueMessagingTemplate;

  @Value("${aws.sqs.seon-blackbox.producer.queue-name}")
  private String awsSqsSeonBlackboxProducerQueueName;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public void sendResponseToQueue(RiskResponse riskResponse) {
    try {
      queueMessagingTemplate.convertAndSend(awsSqsSeonBlackboxProducerQueueName, riskResponse.toString());
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while placing message to the seon-blackbox-checks response queue. " + e.getMessage());
    }
  }
}
