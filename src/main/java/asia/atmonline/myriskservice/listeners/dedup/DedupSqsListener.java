package asia.atmonline.myriskservice.listeners.dedup;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.services.dedup.DeduplicationChecksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DedupSqsListener extends BaseSqsListener {

  private final RiskServiceEngine<DeduplicationChecksService> engine;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public DedupSqsListener(AsyncTaskExecutor threadPoolQueue, DeduplicationChecksService deduplicationChecksService) {
    super(threadPoolQueue);
    this.engine = new RiskServiceEngine<>(deduplicationChecksService);
  }

  @SqsListener(value = "${aws.sqs.dedup.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(RiskRequestJpaEntity request) {
    try {
      log.info(request.toString());
      super.listenQueue(request, engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the dedup-checks request queue. " + e.getMessage()
          + " received message = " + request.toString());
    }
  }
}