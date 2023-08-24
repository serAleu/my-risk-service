package asia.atmonline.myriskservice.producers.bureau;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BureauSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.bureau.producer.queue-name}")
  private String awsSqsBureauProducerQueueName;

  public BureauSqsProducer(SqsTemplate template) {
    super(template);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, awsSqsBureauProducerQueueName);
  }
}