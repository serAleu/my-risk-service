package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.EXPERIAN;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExperianChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
//  private final ExperianChecksService experianChecksService;
  private final PayloadMapper payloadMapper;
  private static final String EXPERIAN_MOCK_WAS_USED_MESSAGE = "EXPERIAN MOCK WAS USED";

  @Value("${aws.sqs.experian.producer.queue-name}")
  private String experianChecksResponseQueue;
  @Value("${using-mocks.experian}")
  private Boolean usingMocksExperian;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && EXPERIAN.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = getMockApprovedResponse(request, EXPERIAN_MOCK_WAS_USED_MESSAGE);
//    if(usingMocksExperian) {
//      response = getMockApprovedResponse(request, EXPERIAN_MOCK_WAS_USED_MESSAGE);
//    } else {
//      response = experianChecksService.process(request);
//    }
    defaultProducer.send(convertToPayload(response), experianChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
