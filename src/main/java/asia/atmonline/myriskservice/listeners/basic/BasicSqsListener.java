package asia.atmonline.myriskservice.listeners.basic;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.BasicRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.BasicResponseJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.BasicRequest;
import asia.atmonline.myriskservice.services.basic.BasicChecksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BasicSqsListener extends BaseSqsListener<BasicRequest> {

  private final RiskServiceEngine<BasicRequest, BasicRequestJpaEntity, BasicResponseJpaEntity, BasicChecksService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public BasicSqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<BasicRequest, BasicRequestJpaEntity, BasicResponseJpaEntity, BasicChecksService> engine, ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.basic.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, BasicRequest.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the basic-checks request queue. " + e.getMessage());
    }
  }
}