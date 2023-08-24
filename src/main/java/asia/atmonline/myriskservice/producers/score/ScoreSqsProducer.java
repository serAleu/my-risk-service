package asia.atmonline.myriskservice.producers.score;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ScoreSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.score.producer.queue-name}")
  private String awsSqsScoreProducerQueueName;

  public ScoreSqsProducer(SqsTemplate template) {
    super(template);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, awsSqsScoreProducerQueueName);
  }
}