package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BASIC;

import asia.atmonline.myriskservice.consumer.payload.RequestPayload;
import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.basic.BasicChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!mock")
@RequiredArgsConstructor
public class BasicCheckRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final BasicChecksService basicChecksService;
  private final PayloadMapper payloadMapper;

  @Value("${aws.sqs.basic.producer.queue-name}")
  private String basicChecksResponseQueue;

  @Override
  public boolean isSuitable(RequestPayload payload) {
    return BASIC.equals(payload.getCheckType());
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = basicChecksService.process(request);
    defaultProducer.send(convertToPayload(response), basicChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
