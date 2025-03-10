package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.COOLDOWN;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.cooldown.CooldownChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CooldownChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final CooldownChecksService cooldownChecksService;
  private final PayloadMapper payloadMapper;
  private static final String COOLDOWN_MOCK_WAS_USED = "COOLDOWN MOCK WAS USED";

  @Value("${aws.sqs.cooldown.producer.queue-name}")
  private String cooldownChecksResponseQueue;
  @Value("${using-mocks.cooldown}")
  private Boolean usingMocksCooldown;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && COOLDOWN.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response;
    if(usingMocksCooldown) {
      response = getMockApprovedResponse(request, COOLDOWN_MOCK_WAS_USED);
    } else {
      response = cooldownChecksService.process(request);
    }
    defaultProducer.send(convertToPayload(response), cooldownChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
