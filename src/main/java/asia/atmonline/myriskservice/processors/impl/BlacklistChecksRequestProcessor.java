package asia.atmonline.myriskservice.processors.impl;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BL;

import asia.atmonline.myriskservice.consumer.payload.ResponsePayload;
import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.mapper.PayloadMapper;
import asia.atmonline.myriskservice.processors.BaseRequestProcessor;
import asia.atmonline.myriskservice.producers.DefaultProducer;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlacklistChecksRequestProcessor extends BaseRequestProcessor {

  private final DefaultProducer defaultProducer;
  private final BlacklistChecksService blacklistChecksService;
  private final PayloadMapper payloadMapper;
  private static final String BLACKLIST_MOCK_WAS_USED_MESSAGE = "BLACKLIST MOCK WAS USED";

  @Value("${aws.sqs.blacklists.producer.queue-name}")
  private String blacklistChecksResponseQueue;
  @Value("${using-mocks.blacklists}")
  private Boolean usingMocksBlacklists;

  @Override
  public boolean isSuitable(RiskRequestJpaEntity request) {
    return request != null && BL.equals(request.getCheckType()) && request.getPhone() != null;
  }

  @Override
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response;
    if(usingMocksBlacklists) {
      return getMockApprovedResponse(request, BLACKLIST_MOCK_WAS_USED_MESSAGE);
    } else {
      response = blacklistChecksService.process(request);
    }
    defaultProducer.send(convertToPayload(response), blacklistChecksResponseQueue);
    return response;
  }

  private ResponsePayload convertToPayload(RiskResponseJpaEntity response) {
    return payloadMapper.entityToPayload(response);
  }
}
