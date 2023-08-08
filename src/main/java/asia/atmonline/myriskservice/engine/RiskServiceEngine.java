package asia.atmonline.myriskservice.engine;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.messages.request.BaseRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Scope("prototype")
public class RiskServiceEngine<R extends BaseRequest, E extends BaseJpaEntity, Y extends BaseJpaEntity,
    S extends BaseChecksService<R, E, Y>> {

  private final S service;

  public void process(R request) {
    service.save(service.getRequestEntity(request));
    if(service.accept(request)) {
      RiskResponseJpaEntity<? extends BaseSqsProducer> response = service.process(request);
      service.save(response);
      response.getProducer().sendResponse(response);
    } else {
      log.warn("Unacceptable request to Risk Service. request = " + request.toString());
    }
  }
}