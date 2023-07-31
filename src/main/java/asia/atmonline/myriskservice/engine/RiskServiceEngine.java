package asia.atmonline.myriskservice.engine;

import asia.atmonline.myriskservice.messages.request.BaseRequest;
import asia.atmonline.myriskservice.messages.request.impl.BasicRequest;
import asia.atmonline.myriskservice.messages.request.impl.BlacklistsRequest;
import asia.atmonline.myriskservice.messages.request.impl.BureauRequest;
import asia.atmonline.myriskservice.messages.request.impl.Dedup2Request;
import asia.atmonline.myriskservice.messages.request.impl.Dedup3Request;
import asia.atmonline.myriskservice.messages.request.impl.FinalRequest;
import asia.atmonline.myriskservice.messages.request.impl.SeonBlackboxRequest;
import asia.atmonline.myriskservice.messages.request.impl.SeonDataRequest;
import asia.atmonline.myriskservice.messages.response.RiskResponse;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.producers.bl.BLSqsProducer;
import asia.atmonline.myriskservice.producers.bureau.BureauSqsProducer;
import asia.atmonline.myriskservice.producers.dedup2.Dedup2SqsProducer;
import asia.atmonline.myriskservice.producers.dedup3.Dedup3SqsProducer;
import asia.atmonline.myriskservice.producers.fin.FinalSqsProducer;
import asia.atmonline.myriskservice.producers.seon_blackbox.SeonBlackboxSqsProducer;
import asia.atmonline.myriskservice.producers.seon_data.SeonDataSqsProducer;
import asia.atmonline.myriskservice.services.BaseChecksService;
import asia.atmonline.myriskservice.services.basic.BasicChecksService;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import asia.atmonline.myriskservice.services.bureau.BureauChecksService;
import asia.atmonline.myriskservice.services.dedup.Dedup2ChecksService;
import asia.atmonline.myriskservice.services.dedup.Dedup3ChecksService;
import asia.atmonline.myriskservice.services.fin.FinalChecksService;
import asia.atmonline.myriskservice.services.seon.blackbox.SeonBlackboxService;
import asia.atmonline.myriskservice.services.seon.data.SeonDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
//public class RiskServiceEngine<R extends BaseRequest, S extends BaseChecksService<R>> {
public class RiskServiceEngine {

  private final ObjectMapper mapper;
  private final BasicChecksService basicChecksService;
  private final BlacklistChecksService blacklistChecksService;
  private final BureauChecksService bureauChecksService;
  private final Dedup2ChecksService dedup2ChecksService;
  private final Dedup3ChecksService dedup3ChecksService;
  private final SeonBlackboxService seonBlackboxService;
  private final SeonDataService seonDataService;
  private final FinalChecksService finalChecksService;
  private final BasicSqsProducer basicSqsProducer;
  private final BLSqsProducer blSqsProducer;
  private final BureauSqsProducer bureauSqsProducer;
  private final Dedup2SqsProducer dedup2SqsProducer;
  private final Dedup3SqsProducer dedup3SqsProducer;
  private final FinalSqsProducer finalSqsProducer;
  private final SeonDataSqsProducer seonDataSqsProducer;
  private final SeonBlackboxSqsProducer seonBlackboxSqsProducer;

//  public void process(String message) {
//    R request = mapper.readValue(message, R.class);
//    S service;
//    service.saveRequest(request);
//    RiskResponse response = service.process(request);
//    service.saveResponse(response);
//  }

  public void processBlacklistsRequest(String message) throws JsonProcessingException {
    BlacklistsRequest request = mapper.readValue(message, BlacklistsRequest.class);
    if(blacklistChecksService.accept(request)) {
      blacklistChecksService.saveRequest(request);
      RiskResponse response = blacklistChecksService.process(request);
      blacklistChecksService.saveResponse(response);
      blSqsProducer.sendResponseToQueue(response);
    }
  }

  public void processDedup2Request(String message) throws JsonProcessingException {
    Dedup2Request request = mapper.readValue(message, Dedup2Request.class);
    if(dedup2ChecksService.accept(request)) {
      dedup2ChecksService.saveRequest(request);
      RiskResponse response = dedup2ChecksService.process(request);
      dedup2ChecksService.saveResponse(response);
      dedup2SqsProducer.sendResponseToQueue(response);
    }
  }

  public void processDedup3Request(String message) throws JsonProcessingException {
    Dedup3Request request = mapper.readValue(message, Dedup3Request.class);
    if(dedup3ChecksService.accept(request)) {
      dedup3ChecksService.saveRequest(request);
      RiskResponse response = dedup3ChecksService.process(request);
      dedup3ChecksService.saveResponse(response);
      dedup3SqsProducer.sendResponseToQueue(response);
    }
  }

  public void processBasicRequest(String message) throws JsonProcessingException {
    BasicRequest request = mapper.readValue(message, BasicRequest.class);
    if(basicChecksService.accept(request)) {
      basicChecksService.saveRequest(request);
      RiskResponse response = basicChecksService.process(request);
      basicChecksService.saveResponse(response);
      basicSqsProducer.sendResponseToQueue(response);
    }

  }

  public void processSeonDataRequest(String message) throws JsonProcessingException {
    SeonDataRequest request = mapper.readValue(message, SeonDataRequest.class);
    if(seonDataService.accept(request)) {
      seonDataService.saveRequest(request);
      RiskResponse response = seonDataService.process(request);
      seonDataService.saveResponse(response);
      seonDataSqsProducer.sendResponseToQueue(response);
    }
  }

  public void processSeonBlackboxRequest(String message) throws JsonProcessingException {
    SeonBlackboxRequest request = mapper.readValue(message, SeonBlackboxRequest.class);
    if(seonBlackboxService.accept(request)) {
      seonBlackboxService.saveRequest(request);
      RiskResponse response = seonBlackboxService.process(request);
      seonBlackboxService.saveResponse(response);
      seonBlackboxSqsProducer.sendResponseToQueue(response);
    }
  }

  public void processBureauRequest(String message) throws JsonProcessingException {
    BureauRequest request = mapper.readValue(message, BureauRequest.class);
    if(bureauChecksService.accept(request)) {
      bureauChecksService.saveRequest(request);
      RiskResponse response = bureauChecksService.process(request);
      bureauChecksService.saveResponse(response);
      bureauSqsProducer.sendResponseToQueue(response);
    }
  }

  public void processFinalRequest(String message) throws JsonProcessingException {
    FinalRequest request = mapper.readValue(message, FinalRequest.class);
    if(finalChecksService.accept(request)) {
      finalChecksService.saveRequest(request);
      RiskResponse response = finalChecksService.process(request);
      finalChecksService.saveResponse(response);
      finalSqsProducer.sendResponseToQueue(response);
    }
  }
}