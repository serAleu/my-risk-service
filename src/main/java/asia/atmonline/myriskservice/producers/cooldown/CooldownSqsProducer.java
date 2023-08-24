package asia.atmonline.myriskservice.producers.cooldown;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CooldownSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.cooldown.producer.queue-name}")
  private String awsSqsCooldownProducerQueueName;

  public CooldownSqsProducer(SqsTemplate template) {
    super(template);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, awsSqsCooldownProducerQueueName);
  }
}