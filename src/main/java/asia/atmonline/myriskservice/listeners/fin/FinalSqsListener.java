package asia.atmonline.myriskservice.listeners.fin;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.FinalRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.FinalResponseJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.FinalRequest;
import asia.atmonline.myriskservice.services.fin.FinalChecksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FinalSqsListener extends BaseSqsListener<FinalRequest> {

  private final RiskServiceEngine<FinalRequest, FinalRequestJpaEntity, FinalResponseJpaEntity, FinalChecksService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public FinalSqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<FinalRequest, FinalRequestJpaEntity, FinalResponseJpaEntity, FinalChecksService> engine, ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.final.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, FinalRequest.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the final-checks request queue. " + e.getMessage());
    }
  }
}