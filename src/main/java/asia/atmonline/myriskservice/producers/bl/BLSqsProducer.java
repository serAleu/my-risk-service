package asia.atmonline.myriskservice.producers.bl;

import asia.atmonline.myriskservice.messages.response.RiskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BLSqsProducer {

  private final QueueMessagingTemplate queueMessagingTemplate;

  @Value("${aws.sqs.blacklists.producer.queue-name}")
  private String awsSqsBlacklistsProducerQueueName;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public void sendResponseToQueue(RiskResponse riskResponse) {
    try {
      queueMessagingTemplate.convertAndSend(awsSqsBlacklistsProducerQueueName, riskResponse.toString());
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while placing message to the blacklist-checks response queue. " + e.getMessage());
    }
  }
}