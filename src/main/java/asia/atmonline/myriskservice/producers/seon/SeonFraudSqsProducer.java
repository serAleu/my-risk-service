package asia.atmonline.myriskservice.producers.seon;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SeonFraudSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.seon-fraud.producer.queue-name}")
  private String awsSqsSeonDataProducerQueueName;

  public SeonFraudSqsProducer(SqsTemplate template) {
    super(template);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, awsSqsSeonDataProducerQueueName);
  }
}