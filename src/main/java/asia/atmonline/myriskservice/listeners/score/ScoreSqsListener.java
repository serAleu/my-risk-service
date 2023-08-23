package asia.atmonline.myriskservice.listeners.score;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.listeners.BaseSqsListener;
import asia.atmonline.myriskservice.services.score.ScoreChecksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScoreSqsListener extends BaseSqsListener {

  private final RiskServiceEngine<ScoreChecksService> engine;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public ScoreSqsListener(AsyncTaskExecutor threadPoolQueue, ScoreChecksService scoreChecksService) {
    super(threadPoolQueue);
    this.engine = new RiskServiceEngine<>(scoreChecksService);
  }

  @SqsListener(value = "${aws.sqs.score.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(RiskRequestJpaEntity request) {
    try {
      super.listenQueue(request, engine);
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while processing message from the score-checks request queue. " + e.getMessage()
          + " received message = " + request.toString());
    }
  }
}