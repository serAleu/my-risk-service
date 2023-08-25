package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BL;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!mock")
@RequiredArgsConstructor
public class BlacklistChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final BlacklistChecksService blacklistChecksService;
  private final PayloadMapper payloadMapper;

  @Value("${aws.sqs.blacklists.producer.queue-name}")
  private String blacklistChecksResponseQueue;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && BL.equals(request.getCheckType()) && request.getPhone() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = blacklistChecksService.process(request);
    defaultProducer.send(convertToPayload(response), blacklistChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
