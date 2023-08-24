package asia.atmonline.myriskservice.producers;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultProducer {

  public static final String HEADER_CONTENT_TYPE_VALUE = "application/json;charset=UTF-8";
  public static final String HEADER_CONTENT_TYPE = "contentType";

  private final SqsTemplate template;

  public void send(ResponsePayload response, String queueName) {
    Message<?> message = MessageBuilder.withPayload(response)
        .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE)
        .build();

    SendResult<?> result = template.send(queueName, message);
    log.info(result.toString());
  }

}
