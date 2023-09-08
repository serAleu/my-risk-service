package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.SCORE;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.score.ScoreChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!mock")
@RequiredArgsConstructor
public class ScoreChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final ScoreChecksService scoreChecksService;
  private final PayloadMapper payloadMapper;

  @Value("${aws.sqs.score.producer.queue-name}")
  private String scoreChecksResponseQueue;

  @Override
  public boolean isSuitable(RiskRequestRiskJpaEntity request) {
    return request != null && SCORE.equals(request.getCheckType()) && request.getApplicationId() != null && request.getScoreNodeId() != null;
  }

  @Override
  public RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request) {
    RiskResponseRiskJpaEntity response = scoreChecksService.process(request);
    response.setRequestId(request.getId());
    response.setApplicationId(request.getApplicationId());
    defaultProducer.send(convertToPayload(response), scoreChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseRiskJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
