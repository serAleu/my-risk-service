package asia.atmonline.myriskservice.listeners;

import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class BaseSqsListener {

  private final AsyncTaskExecutor threadPoolQueue;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  @SuppressWarnings({"rawtypes"})
  public void listenQueue(RiskRequestJpaEntity request, RiskServiceEngine engine) {
    threadPoolQueue.submit(() -> engine.process(request));
  }
}