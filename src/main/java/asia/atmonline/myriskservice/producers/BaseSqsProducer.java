package asia.atmonline.myriskservice.producers;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Getter
public abstract class BaseSqsProducer {

  public static final String HEADER_CONTENT_TYPE_VALUE = "application/json;charset=UTF-8";
  public static final String HEADER_CONTENT_TYPE = "contentType";

  private final SqsTemplate template;

  @Value("${spring.config.activate.on-profile}")
  private String activeProfile;

  public abstract void sendResponse(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity);

  public void sendResponseToQueue(RiskResponseJpaEntity<? extends BaseSqsProducer> riskResponseJpaEntity, String queueName) {
    try {
      Message<?> msg = MessageBuilder.withPayload(riskResponseJpaEntity)
          .setHeader(HEADER_CONTENT_TYPE, HEADER_CONTENT_TYPE_VALUE)
          .build();

      SendResult<?> result = template.send(queueName, msg);
      log.info(result.toString());
    } catch (Exception e) {
      log.error("my-risk-service-" + activeProfile + " Error while placing message to the " + queueName + " queue. " + e.getMessage());
    }
  }
}