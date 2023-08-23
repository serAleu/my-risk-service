package asia.atmonline.myriskservice.listeners.basic;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.services.basic.BasicChecksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BasicSqsListener extends BaseSqsListener {

  private final RiskServiceEngine<BasicChecksService> engine;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public BasicSqsListener(AsyncTaskExecutor threadPoolQueue, BasicChecksService basicChecksService) {
    super(threadPoolQueue);
    this.engine = new RiskServiceEngine<>(basicChecksService);
  }

  @SqsListener(value = "${aws.sqs.basic.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(RiskRequestJpaEntity request) {
    try {
      log.info(request.toString());
      super.listenQueue(request, engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the basic-checks request queue. " + e.getMessage()
          + " received message = " + request.toString());
    }
  }
}