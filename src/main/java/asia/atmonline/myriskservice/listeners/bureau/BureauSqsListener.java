package asia.atmonline.myriskservice.listeners.bureau;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.services.bureau.BureauChecksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BureauSqsListener extends BaseSqsListener {

  private final RiskServiceEngine<BureauChecksService> engine;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public BureauSqsListener(AsyncTaskExecutor threadPoolQueue, BureauChecksService bureauChecksService) {
    super(threadPoolQueue);
    this.engine = new RiskServiceEngine<>(bureauChecksService);
  }

  @SqsListener(value = "${aws.sqs.bureau.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(RiskRequestJpaEntity request) {
    try {
      super.listenQueue(request, engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the bureau-checks request queue. " + e.getMessage()
          + " received message = " + request.toString());
    }
  }
}