package asia.atmonline.myriskservice.listeners.seon;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.services.seon.SeonFraudChecksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SeonFraudSqsListener extends BaseSqsListener {

  private final RiskServiceEngine<SeonFraudChecksService> engine;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public SeonFraudSqsListener(AsyncTaskExecutor threadPoolQueue, SeonFraudChecksService seonFraudChecksService) {
    super(threadPoolQueue);
    this.engine = new RiskServiceEngine<>(seonFraudChecksService);
  }

  @SqsListener(value = "${aws.sqs.seon-fraud.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(RiskRequestJpaEntity request) {
    try {
      log.info(request.toString());
      super.listenQueue(request, engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the seon-fraud-checks request queue. " + e.getMessage()
          + " received message = " + request.toString());
    }
  }
}