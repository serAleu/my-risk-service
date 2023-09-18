package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BUREAU;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.bureau.BureauChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!mock")
@RequiredArgsConstructor
public class BureauChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final BureauChecksService bureauChecksService;
  private final PayloadMapper payloadMapper;
  private static final String BUREAU_MOCK_WAS_USED_MESSAGE = "BUREAU MOCK WAS USED";

  @Value("${aws.sqs.bureau.producer.queue-name}")
  private String bureauChecksResponseQueue;
  @Value("${using-mocks.bureau}")
  private Boolean usingMocksBureau;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && BUREAU.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response;
    if(usingMocksBureau) {
      response = getMockApprovedResponse(request, BUREAU_MOCK_WAS_USED_MESSAGE);
    } else {
      response = bureauChecksService.process(request);
    }
    defaultProducer.send(convertToPayload(response), bureauChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
