package asia.atmonline.myriskservice.consumer;

import asia.atmonline.myriskservice.consumer.payload.BlackListRequestPayload;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlackListConsumer {

  private final BlacklistChecksService blacklistChecksService;

  @SneakyThrows
  @SqsListener("${blacklist.request.queue.name}")
  public void listen(BlackListRequestPayload payload) {
    log.info(payload.toString());
    blacklistChecksService.save(
        payload.getApplicationId(),
        payload.getBorrowerId(),
        payload.getUserId(),
        payload.getRejectCode()
    );
  }
}