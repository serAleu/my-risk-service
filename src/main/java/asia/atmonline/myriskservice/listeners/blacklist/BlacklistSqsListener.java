package asia.atmonline.myriskservice.listeners.blacklist;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BlacklistSqsListener extends BaseSqsListener {

  private final RiskServiceEngine<BlacklistChecksService> engine;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public BlacklistSqsListener(AsyncTaskExecutor threadPoolQueue, BlacklistChecksService blacklistChecksService) {
    super(threadPoolQueue);
    this.engine = new RiskServiceEngine<>(blacklistChecksService);
  }

  @SqsListener(value = "${aws.sqs.blacklists.receiver.queue-name}")
  public void listenQueue(RiskRequestJpaEntity request) {
    try {
      log.info(request.toString());
      super.listenQueue(request, engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the blacklists-checks request queue. " + e.getMessage()
          + " received message = " + request.toString());
    }
  }
}