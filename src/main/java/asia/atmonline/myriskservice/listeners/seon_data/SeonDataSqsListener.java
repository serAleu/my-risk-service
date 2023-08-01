package asia.atmonline.myriskservice.listeners.seon_data;

import asia.atmonline.myriskservice.data.entity.impl.requests.impl.SeonDataRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.impl.responses.impl.SeonDataResponseJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.SeonDataRequest;
import asia.atmonline.myriskservice.services.seon.data.SeonDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SeonDataSqsListener extends BaseSqsListener<SeonDataRequest> {

  private final RiskServiceEngine<SeonDataRequest, SeonDataRequestJpaEntity, SeonDataResponseJpaEntity, SeonDataService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public SeonDataSqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<SeonDataRequest, SeonDataRequestJpaEntity, SeonDataResponseJpaEntity, SeonDataService> engine, ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.seon-data.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, SeonDataRequest.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the seon-data-checks request queue. " + e.getMessage());
    }
  }
}