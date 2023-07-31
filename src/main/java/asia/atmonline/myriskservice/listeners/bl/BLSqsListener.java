package asia.atmonline.myriskservice.listeners.bl;

import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.messages.request.impl.BlacklistsRequest;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BLSqsListener {

//  private final RiskServiceEngine<BlacklistsRequest, BlacklistChecksService> engine;
  private final RiskServiceEngine engine;
  private final AsyncTaskExecutor threadPoolQueue;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  @SqsListener(value = "${aws.blacklists.receiver.queue-name}", deletionPolicy = SqsMessageDeletionPolicy.ALWAYS)
  public void listenQueue(String message) {
    threadPoolQueue.submit(() -> {
      try {
        engine.processBlacklistsRequest(message);
      } catch (Exception e) {
        log.error("my-risk-service-" + activeProfile + " Error while processing message from the blacklists-checks request queue. " + e.getMessage());
      }
    });
  }
}