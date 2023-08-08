package asia.atmonline.myriskservice.listeners.dedup2;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.Dedup2RequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.Dedup2ResponseJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.Dedup2Request;
import asia.atmonline.myriskservice.services.dedup.Dedup2ChecksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Dedup2SqsListener extends BaseSqsListener<Dedup2Request> {

  private final RiskServiceEngine<Dedup2Request, Dedup2RequestJpaEntity, Dedup2ResponseJpaEntity, Dedup2ChecksService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public Dedup2SqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<Dedup2Request, Dedup2RequestJpaEntity, Dedup2ResponseJpaEntity, Dedup2ChecksService> engine, ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.dedup-2.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, Dedup2Request.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the dedup2-checks request queue. " + e.getMessage());
    }
  }
}