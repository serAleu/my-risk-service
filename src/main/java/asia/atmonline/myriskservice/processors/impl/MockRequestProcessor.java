package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.APPROVE;

import asia.atmonline.myriskservice.consumer.payload.RequestPayload;
import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.enums.risk.CheckType;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Profile("mock")
public class MockRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final PayloadMapper payloadMapper;

  @Override
  public boolean isSuitable(RequestPayload payload) {
    return true;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    ResponsePayload response = ResponsePayload.builder()
        .checkType(request.getCheckType())
        .decision(APPROVE)
        .rejectionReason(null)
        .borrowerId(1L)
        .applicationId(request.getApplicationId())
        .build();

    defaultProducer.send(response, getQueueName(request.getCheckType()));
    return payloadMapper.payloadToEntity(response);
  }

  private String getQueueName(CheckType type) {
    return switch (type) {
      case BASIC -> "my-risk-basic-response-preprod";
      case BL -> "my-risk-bl-response-preprod";
      case BUREAU -> "my-risk-bureau-response-preprod";
      case COOLDOWN -> "my-risk-cooldown-response-preprod";
      case DEDUP -> "my-risk-dedup-response-preprod";
      case FINAL -> "my-risk-final-response-preprod";
      case SCORE -> "my-risk-score-response-preprod";
      case SEON -> "my-risk-seon-fraud-response-preprod";
      case EXPERIAN -> "my-risk-experian-response-preprod";
      case CREDIT_HISTORY_AVAILABILITY -> "my-risk-credit-history-response-preprod";
    };
  }
}
