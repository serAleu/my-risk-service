package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.DEDUP;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.dedup.DeduplicationChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeduplicationChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final DeduplicationChecksService deduplicationChecksService;
  private final PayloadMapper payloadMapper;
  private static final String DEDUPLICATION_MOCK_WAS_USED_MESSAGE = "DEDUPLICATION MOCK WAS USED";

  @Value("${aws.sqs.dedup.producer.queue-name}")
  private String dedupChecksResponseQueue;
  @Value("${using-mocks.deduplication}")
  private Boolean usingMocksDeduplication;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && DEDUP.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response;
    if(usingMocksDeduplication) {
      response = getMockApprovedResponse(request, DEDUPLICATION_MOCK_WAS_USED_MESSAGE);
    } else {
      response = deduplicationChecksService.process(request);
    }
    defaultProducer.send(convertToPayload(response), dedupChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
