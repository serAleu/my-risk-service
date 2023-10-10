package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.CREDIT_HISTORY_AVAILABILITY;

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
public class CreditHistoryChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  //  private final CreditHistoryChecksService creditHistoryChecksService;
  private final PayloadMapper payloadMapper;
  private static final String CREDIT_HISTORY_MOCK_WAS_USED_MESSAGE = "CREDIT HISTORY MOCK WAS USED";

  @Value("${aws.sqs.credit-history.producer.queue-name}")
  private String creditHistoryChecksResponseQueue;
//  @Value("${using-mocks.credit-history}")
//  private Boolean usingMocksExperian;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && CREDIT_HISTORY_AVAILABILITY.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = getMockApprovedResponse(request, CREDIT_HISTORY_MOCK_WAS_USED_MESSAGE);
//    if(usingMocksExperian) {
//      response = getMockApprovedResponse(request, EXPERIAN_MOCK_WAS_USED_MESSAGE);
//    } else {
//      response = experianChecksService.process(request);
//    }
    defaultProducer.send(convertToPayload(response), creditHistoryChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
