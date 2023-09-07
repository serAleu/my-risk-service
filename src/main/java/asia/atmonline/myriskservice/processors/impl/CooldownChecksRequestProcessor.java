package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.COOLDOWN;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.cooldown.CooldownChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!mock")
@RequiredArgsConstructor
public class CooldownChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final CooldownChecksService cooldownChecksService;
  private final PayloadMapper payloadMapper;

  @Value("${aws.sqs.cooldown.producer.queue-name}")
  private String cooldownChecksResponseQueue;

  @Override
  public boolean isSuitable(RiskRequestRiskJpaEntity request) {
    return request != null && COOLDOWN.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request) {
    RiskResponseRiskJpaEntity response = cooldownChecksService.process(request);
    defaultProducer.send(convertToPayload(response), cooldownChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseRiskJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
