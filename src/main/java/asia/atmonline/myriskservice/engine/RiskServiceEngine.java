package asia.atmonline.myriskservice.engine;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.APPROVE;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.messages.request.BaseRequest;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.producers.blacklist.BlacklistSqsProducer;
import asia.atmonline.myriskservice.producers.bureau.BureauSqsProducer;
import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.producers.fin.FinalSqsProducer;
import asia.atmonline.myriskservice.producers.seon.SeonFraudSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Scope("prototype")
public class RiskServiceEngine<R extends BaseRequest, E extends BaseJpaEntity, S extends BaseChecksService<R, E>> {

  private final S service;

  public void process(R request) {
//    service.save(service.getRequestEntity(request));
//    if (service.accept(request)) {
//      RiskResponseJpaEntity<? extends BaseSqsProducer> response = service.process(request);
    /* just delete code from here -> */
    RiskResponseJpaEntity<? extends BaseSqsProducer> response = getResponse(request);
    response.setDecision(APPROVE);
    response.setCheck(request.getCheck());
    response.setBorrowerId(response.getBorrowerId());
    response.setCreditApplicationId(request.getCreditApplicationId());
    response.setAdditionalField("additional_field", "Hey! I'm a message from Risk service!");
    try {
      service.save(service.getRequestEntity(request));
      service.save(response);
    } catch (Exception e) {
      log.error("Error while saving message " + e.getMessage());
    }
    /* -> to here before upload risk service for QA testing */
    response.getProducer().sendResponse(response);
//    service.save(response);
//    } else {
//      log.warn("Unacceptable request to Risk Service. request = " + request.toString());
//    }
  }

  private RiskResponseJpaEntity<? extends BaseSqsProducer> getResponse(R request) {
    return switch (request.getCheck()) {
      case BASIC -> new RiskResponseJpaEntity<BasicSqsProducer>();
      case BL -> new RiskResponseJpaEntity<BlacklistSqsProducer>();
      case BUREAU -> new RiskResponseJpaEntity<BureauSqsProducer>();
      case COOLDOWN -> new RiskResponseJpaEntity<CooldownSqsProducer>();
      case DEDUP -> new RiskResponseJpaEntity<DeduplicationSqsProducer>();
      case FINAL -> new RiskResponseJpaEntity<FinalSqsProducer>();
      case SCORE -> null;
      case SEON -> new RiskResponseJpaEntity<SeonFraudSqsProducer>();
    };
  }
}