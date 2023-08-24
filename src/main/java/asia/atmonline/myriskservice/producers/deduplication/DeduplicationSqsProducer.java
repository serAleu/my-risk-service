package asia.atmonline.myriskservice.producers.deduplication;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationSqsProducer extends BaseSqsProducer {

  @Value("${aws.sqs.dedup.producer.queue-name}")
  private String awsSqsDedupProducerQueueName;

  public DeduplicationSqsProducer(SqsTemplate template) {
    super(template);
  }

  @Override
  public void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity) {
    super.sendResponseToQueue(riskResponseJpaEntity, awsSqsDedupProducerQueueName);
  }
}