package asia.atmonline.myriskservice.listeners.blacklist;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.BlacklistRequestJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.BlacklistsRequest;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BlacklistSqsListener extends BaseSqsListener<BlacklistsRequest> {

  private final RiskServiceEngine<BlacklistsRequest, BlacklistRequestJpaEntity, BlacklistChecksService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public BlacklistSqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<BlacklistsRequest, BlacklistRequestJpaEntity, BlacklistChecksService> engine, ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.blacklists.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, BlacklistsRequest.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the blacklists-checks request queue. " + e.getMessage()
          + " received message = " + message);
    }
  }
}