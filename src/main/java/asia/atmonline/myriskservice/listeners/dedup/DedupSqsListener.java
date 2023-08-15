package asia.atmonline.myriskservice.listeners.dedup;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.DedupRequestJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.DeduplicationRequest;
import asia.atmonline.myriskservice.services.dedup.DeduplicationChecksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DedupSqsListener extends BaseSqsListener<DeduplicationRequest> {

  private final RiskServiceEngine<DeduplicationRequest, DedupRequestJpaEntity, DeduplicationChecksService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public DedupSqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<DeduplicationRequest, DedupRequestJpaEntity, DeduplicationChecksService> engine,
      ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.dedup.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, DeduplicationRequest.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the dedup-checks request queue. " + e.getMessage()
          + " received message = " + message);
    }
  }
}