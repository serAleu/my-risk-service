package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.SEON;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.seon.SeonFraudChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeonChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final SeonFraudChecksService seonFraudChecksService;
  private final PayloadMapper payloadMapper;
  private static final String SEON_MOCK_WAS_USED_MESSAGE = "SEON MOCK WAS USED";

  @Value("${aws.sqs.seon-fraud.producer.queue-name}")
  private String seonFraudChecksResponseQueue;
  @Value("${using-mocks.seon}")
  private Boolean usingMocksSeon;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && SEON.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response;
    if(usingMocksSeon) {
      response = getMockApprovedResponse(request, SEON_MOCK_WAS_USED_MESSAGE);
    } else {
      response = seonFraudChecksService.process(request);
    }
    defaultProducer.send(convertToPayload(response), seonFraudChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
