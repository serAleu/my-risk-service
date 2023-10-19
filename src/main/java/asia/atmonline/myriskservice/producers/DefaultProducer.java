package asia.atmonline.myriskservice.producers;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.core.SqsMessageHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaultProducer {

  public static final String HEADER_CONTENT_TYPE_VALUE = "application/json;charset=UTF-8";
  public static final String HEADER_CONTENT_TYPE = "contentType";
  private final QueueMessagingTemplate queueMessagingTemplate;

  public void send(ResponsePayload response, String queueName) {
    log.info("RequestPayload = " + response.toString());
    Map<String, Object> headers = new HashMap<>();
    headers.put(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE);
    MessageHeaders headersCustom = new SqsMessageHeaders(headers);
    queueMessagingTemplate.convertAndSend(queueName, response, headersCustom);
  }

}
