package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.FINAL;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.fin.FinalChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!mock")
@RequiredArgsConstructor
public class FinalChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final FinalChecksService finalChecksService;
  private final PayloadMapper payloadMapper;

  @Value("${aws.sqs.final.producer.queue-name}")
  private String finalChecksResponseQueue;

  @Override
  public boolean isSuitable(RiskRequestRiskJpaEntity request) {
    return request != null && FINAL.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request) {
    RiskResponseRiskJpaEntity response = finalChecksService.process(request);
    defaultProducer.send(convertToPayload(response), finalChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseRiskJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
