package asia.atmonline.myriskservice.listeners.seon;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.SeonFraudRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.SeonFraudResponseJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.SeonFraudRequest;
import asia.atmonline.myriskservice.services.seon.SeonFraudService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SeonFraudSqsListener extends BaseSqsListener<SeonFraudRequest> {

  private final RiskServiceEngine<SeonFraudRequest, SeonFraudRequestJpaEntity, SeonFraudResponseJpaEntity, SeonFraudService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public SeonFraudSqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<SeonFraudRequest, SeonFraudRequestJpaEntity, SeonFraudResponseJpaEntity, SeonFraudService> engine, ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.seon-fraud.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, SeonFraudRequest.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the seon-fraud-checks request queue. " + e.getMessage());
    }
  }
}