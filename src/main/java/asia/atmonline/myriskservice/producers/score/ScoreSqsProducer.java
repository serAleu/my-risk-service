package asia.atmonline.myriskservice.producers.score;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScoreSqsProducer {

  private final QueueMessagingTemplate queueMessagingTemplate;

  @Value("${aws.sqs.score.producer.queue-name}")
  private String awsSqsScoreProducerQueueName;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public void sendResponseToQueue(String response) {
    try {
      queueMessagingTemplate.convertAndSend(awsSqsScoreProducerQueueName, response);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while placing message to the score-check response queue. " + e.getMessage());
    }
  }
}