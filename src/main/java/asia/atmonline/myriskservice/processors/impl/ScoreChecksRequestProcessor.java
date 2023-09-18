package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.SCORE;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.score.ScoreChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoreChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final ScoreChecksService scoreChecksService;
  private final PayloadMapper payloadMapper;
  private static final String SCORE_MOCK_WAS_USED_MESSAGE = "BUREAU MOCK WAS USED";

  @Value("${aws.sqs.score.producer.queue-name}")
  private String scoreChecksResponseQueue;
  @Value("${using-mocks.score}")
  private Boolean usingMocksScore;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && SCORE.equals(request.getCheckType()) && request.getApplicationId() != null && request.getScoreNodeId() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response;
    if(usingMocksScore) {
      response = getMockApprovedResponse(request, SCORE_MOCK_WAS_USED_MESSAGE);
    } else {
      response = scoreChecksService.process(request);
    }
    defaultProducer.send(convertToPayload(response), scoreChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
