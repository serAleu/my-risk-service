package asia.atmonline.myriskservice.listeners.cooldown;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.services.cooldown.CooldownChecksService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CooldownSqsListener extends BaseSqsListener {

  private final RiskServiceEngine<CooldownChecksService> engine;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public CooldownSqsListener(AsyncTaskExecutor threadPoolQueue, CooldownChecksService cooldownChecksService) {
    super(threadPoolQueue);
    this.engine = new RiskServiceEngine<>(cooldownChecksService);
  }

  @SqsListener(value = "${aws.sqs.cooldown.receiver.queue-name}")
  public void listenQueue(RiskRequestJpaEntity request) {
    try {
      log.info(request.toString());
      super.listenQueue(request, engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the cooldown-checks request queue. " + e.getMessage()
          + " received message = " + request.toString());
    }
  }
}