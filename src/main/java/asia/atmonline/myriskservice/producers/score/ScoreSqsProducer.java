package asia.atmonline.myriskservice.producers.score;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class ScoreSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.score.producer.queue-name}")
  private String awsSqsScoreProducerQueueName;

  public ScoreSqsProducer(QueueMessagingTemplate queueMessagingTemplate) {
    super(queueMessagingTemplate);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, "my-risk-score-response-preprod");
  }
}