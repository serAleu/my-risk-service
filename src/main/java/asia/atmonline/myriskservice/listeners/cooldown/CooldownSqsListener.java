package asia.atmonline.myriskservice.listeners.cooldown;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.CooldownRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.CooldownResponseJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.CooldownRequest;
import asia.atmonline.myriskservice.services.cooldown.CooldownChecksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CooldownSqsListener extends BaseSqsListener<CooldownRequest> {

  private final RiskServiceEngine<CooldownRequest, CooldownRequestJpaEntity, CooldownResponseJpaEntity, CooldownChecksService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public CooldownSqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<CooldownRequest, CooldownRequestJpaEntity, CooldownResponseJpaEntity, CooldownChecksService> engine, ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.cooldown.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, CooldownRequest.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the cooldown-checks request queue. " + e.getMessage());
    }
  }
}