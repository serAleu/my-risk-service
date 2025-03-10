package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BASIC;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.basic.BasicChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasicCheckRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final BasicChecksService basicChecksService;
  private final PayloadMapper payloadMapper;
  private static final String BASIC_MOCK_WAS_USED_MESSAGE = "BASIC MOCK WAS USED";

  @Value("${aws.sqs.basic.producer.queue-name}")
  private String basicChecksResponseQueue;
  @Value("${using-mocks.basic}")
  private Boolean usingMocksBasic;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && BASIC.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response;
    if(usingMocksBasic) {
      response = getMockApprovedResponse(request, BASIC_MOCK_WAS_USED_MESSAGE);
    } else {
      response = basicChecksService.process(request);
    }
    defaultProducer.send(convertToPayload(response), basicChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
