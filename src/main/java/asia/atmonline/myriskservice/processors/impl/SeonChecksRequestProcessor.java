package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.SEON;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.seon.SeonFraudChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!mock")
@RequiredArgsConstructor
public class SeonChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final SeonFraudChecksService seonFraudChecksService;
  private final PayloadMapper payloadMapper;

  @Value("${aws.sqs.seon-fraud.producer.queue-name}")
  private String seonFraudChecksResponseQueue;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && SEON.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = seonFraudChecksService.process(request);
    defaultProducer.send(convertToPayload(response), seonFraudChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
