package asia.atmonline.myriskservice.listeners;

import asia.atmonline.myriskservice.engine.RiskServiceEngine;
import asia.atmonline.myriskservice.messages.request.BaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class BaseSqsListener<T extends BaseRequest> {

  private final AsyncTaskExecutor threadPoolQueue;
  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void listenQueue(T request, RiskServiceEngine engine) {
    threadPoolQueue.submit(() -> engine.process(request));
  }
}