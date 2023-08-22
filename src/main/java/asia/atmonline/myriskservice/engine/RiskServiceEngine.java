package asia.atmonline.myriskservice.engine;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.APPROVE;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.producers.blacklist.BlacklistSqsProducer;
import asia.atmonline.myriskservice.producers.bureau.BureauSqsProducer;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.producers.fin.FinalSqsProducer;
import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
import asia.atmonline.myriskservice.producers.seon.SeonFraudSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Scope("prototype")
public class RiskServiceEngine<S extends BaseChecksService> {

  private final S service;
  @Autowired
  private QueueMessagingTemplate queueMessagingTemplate;

//  public void process(RiskRequestJpaEntity request) {
//    Long requestId = service.save(request);
//    if (service.accept(request)) {
//      RiskResponseJpaEntity<? extends BaseSqsProducer> response = service.process(request);
//      response.setRequestId(requestId);
//      response.getProducer().sendResponse(response);
//      service.save(response);
//    } else {
//      log.warn("Unacceptable request to Risk Service. request = " + request.toString());
//    }
//  }


  /* just delete code from here -> */
  public void process(RiskRequestJpaEntity request) {
//    BaseSqsProducer producer = defineSqsProducer(request);
    RiskResponseJpaEntity<? extends BaseSqsProducer> response = getResponse(request);
    response.setDecision(APPROVE);
    response.setCheck(request.getCheckType());
    response.setBorrowerId(response.getBorrowerId());
    response.setCreditApplicationId(request.getCreditApplicationId());
    response.setBorrowerId(1L);
//    response.setAdditionalField("additional_field", "Hey! I'm a message from Risk service!");
    try {
      response.setRequestId(service.save(request));
      service.save(response);
      defineSqsProducer(request).sendResponse(response);
    } catch (Exception e) {
      log.error("Error while saving message " + e.getMessage());
    }
  }

  private BaseSqsProducer defineSqsProducer(RiskRequestJpaEntity request) {
    return switch (request.getCheckType()) {
      case BASIC -> new BasicSqsProducer(queueMessagingTemplate);
      case BL -> new BlacklistSqsProducer(queueMessagingTemplate);
      case BUREAU -> new BureauSqsProducer(queueMessagingTemplate);
      case COOLDOWN -> new CooldownSqsProducer(queueMessagingTemplate);
      case DEDUP -> new DeduplicationSqsProducer(queueMessagingTemplate);
      case FINAL -> new FinalSqsProducer(queueMessagingTemplate);
      case SCORE -> new ScoreSqsProducer(queueMessagingTemplate);
      case SEON -> new SeonFraudSqsProducer(queueMessagingTemplate);
    };
  }

  private RiskResponseJpaEntity<? extends BaseSqsProducer> getResponse(RiskRequestJpaEntity request) {
    return switch (request.getCheckType()) {
      case BASIC -> new RiskResponseJpaEntity<BasicSqsProducer>();
      case BL -> new RiskResponseJpaEntity<BlacklistSqsProducer>();
      case BUREAU -> new RiskResponseJpaEntity<BureauSqsProducer>();
      case COOLDOWN -> new RiskResponseJpaEntity<CooldownSqsProducer>();
      case DEDUP -> new RiskResponseJpaEntity<DeduplicationSqsProducer>();
      case FINAL -> new RiskResponseJpaEntity<FinalSqsProducer>();
      case SCORE -> new RiskResponseJpaEntity<ScoreSqsProducer>();
      case SEON -> new RiskResponseJpaEntity<SeonFraudSqsProducer>();
    };
  }
}