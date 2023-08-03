package asia.atmonline.myriskservice.listeners.dedup3;

import asia.atmonline.myriskservice.data.entity.requests.impl.Dedup3RequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.responses.impl.Dedup3ResponseJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.Dedup3Request;
import asia.atmonline.myriskservice.services.dedup.Dedup3ChecksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Dedup3SqsListener extends BaseSqsListener<Dedup3Request> {

  private final RiskServiceEngine<Dedup3Request, Dedup3RequestJpaEntity, Dedup3ResponseJpaEntity, Dedup3ChecksService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public Dedup3SqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<Dedup3Request, Dedup3RequestJpaEntity, Dedup3ResponseJpaEntity, Dedup3ChecksService> engine,
      ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.dedup-3.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, Dedup3Request.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the dedup3-checks request queue. " + e.getMessage());
    }
  }
}