package asia.atmonline.myriskservice.producers;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public abstract class BaseSqsProducer {

  private final QueueMessagingTemplate queueMessagingTemplate;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public abstract void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity);

  public void sendResponseToQueue(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity, String queueName) {
    try {
      queueMessagingTemplate.convertAndSend(queueName, riskResponseJpaEntity.toString());
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while placing message to the " + queueName + " queue. " + e.getMessage());
    }
  }
}