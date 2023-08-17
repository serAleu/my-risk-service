package asia.atmonline.myriskservice.listeners.score;

import asia.atmonline.myriskservice.data.entity.risk.requests.impl.ScoreRequestJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.messages.request.impl.ScoreRequest;
import asia.atmonline.myriskservice.services.score.ScoreChecksService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScoreSqsListener extends BaseSqsListener<ScoreRequest> {

  private final RiskServiceEngine<ScoreRequest, ScoreRequestJpaEntity, ScoreChecksService> engine;
  private final ObjectMapper mapper;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public ScoreSqsListener(AsyncTaskExecutor threadPoolQueue,
      RiskServiceEngine<ScoreRequest, ScoreRequestJpaEntity, ScoreChecksService> engine, ObjectMapper mapper) {
    super(threadPoolQueue);
    this.engine = engine;
    this.mapper = mapper;
  }

  @SqsListener(value = "${aws.sqs.score.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    try {
      super.listenQueue(mapper.readValue(message, ScoreRequest.class), engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the score-checks request queue. " + e.getMessage()
          + " received message = " + message);
    }
  }
}