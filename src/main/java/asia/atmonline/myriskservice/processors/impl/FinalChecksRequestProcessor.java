package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.FINAL;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.fin.FinalChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinalChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final FinalChecksService finalChecksService;
  private final PayloadMapper payloadMapper;
  private static final String FINAL_MOCK_WAS_USED_MESSAGE = "FINAL MOCK WAS USED";

  @Value("${aws.sqs.final.producer.queue-name}")
  private String finalChecksResponseQueue;
  @Value("${using-mocks.final}")
  private Boolean usingMocksFinal;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && FINAL.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response;
    if(usingMocksFinal) {
      response = getMockApprovedResponse(request, FINAL_MOCK_WAS_USED_MESSAGE);
    } else {
      response = finalChecksService.process(request);
    }
    defaultProducer.send(convertToPayload(response), finalChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
