package asia.atmonline.myriskservice.listeners.seon_blackbox;

import asia.atmonline.myriskservice.data.entity.impl.requests.impl.SeonBlackboxRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.impl.SeonBlackboxResponseJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.SeonBlackboxRequest;
import asia.atmonline.myriskservice.services.seon.blackbox.SeonBlackboxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SeonBlackboxSqsListener extends BaseSqsListener<SeonBlackboxRequest> {

  private final RiskServiceEngine<SeonBlackboxRequest, SeonBlackboxRequestJpaEntity, SeonBlackboxResponseJpaEntity, SeonBlackboxService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public SeonBlackboxSqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<SeonBlackboxRequest, SeonBlackboxRequestJpaEntity, SeonBlackboxResponseJpaEntity, SeonBlackboxService> engine,
      ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.seon-blackbox.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, SeonBlackboxRequest.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the seon-blackbox-checks request queue. " + e.getMessage());
    }
  }
}