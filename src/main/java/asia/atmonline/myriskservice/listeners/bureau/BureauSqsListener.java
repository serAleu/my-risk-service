package asia.atmonline.myriskservice.listeners.bureau;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.BureauRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.BureauResponseJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.BureauRequest;
import asia.atmonline.myriskservice.services.bureau.BureauChecksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BureauSqsListener extends BaseSqsListener<BureauRequest> {

  private final RiskServiceEngine<BureauRequest, BureauRequestJpaEntity, BureauResponseJpaEntity, BureauChecksService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public BureauSqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<BureauRequest, BureauRequestJpaEntity, BureauResponseJpaEntity, BureauChecksService> engine, ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.bureau.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, BureauRequest.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the bureau-checks request queue. " + e.getMessage());
    }
  }
}