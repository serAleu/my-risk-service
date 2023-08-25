package asia.atmonline.myriskservice.consumer;

import asia.atmonline.myriskservice.consumer.payload.RequestPayload;
import asia.atmonline.myriskservice.data.JpaEntityService;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultConsumer {

  private final List<BaseRequestProcessor> processors;

  private final ObjectMapper objectMapper;
  private final PayloadMapper payloadMapper;
  private final JpaEntityService entityService;

  @SneakyThrows
  @SqsListener("#{'${aws.sqs.request.queue}'.split(',')}")
  public void listen(RequestPayload payload) {
    log.info(payload.toString());
    RiskRequestJpaEntity request = entityService.save(payloadMapper.payloadToEntity(payload));
    processors.stream()
        .filter(processor -> processor.isSuitable(request))
        .map(processor -> processor.process(request))
        .forEach(entityService::save);
  }


}
