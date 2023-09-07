package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BUREAU;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
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

  @Value("${aws.sqs.bureau.producer.queue-name}")
  private String bureauChecksResponseQueue;

  @Override
  public boolean isSuitable(RiskRequestRiskJpaEntity request) {
    return request != null && BUREAU.equals(request.getCheckType()) && request.getApplicationId() != null;
  }

  @Override
  public RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request) {
    RiskResponseRiskJpaEntity response = bureauChecksService.process(request);
    defaultProducer.send(convertToPayload(response), bureauChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseRiskJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
