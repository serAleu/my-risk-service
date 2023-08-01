package asia.atmonline.myriskservice.services.score.service;

import asia.atmonline.myriskservice.messages.request.impl.ScoreServiceRequest;
import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
import asia.atmonline.myriskservice.services.score.data.DataService;
import asia.atmonline.myriskservice.services.score.data.repositories.RepositoryScore;
import asia.atmonline.myriskservice.services.score.web.BitbucketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoreServiceEngine {

  private final DataService dataService;
  private final ObjectMapper mapper;
  private final BitbucketService bitbucketService;
  private final RepositoryScore scoreRepository;
  private final ScoreSqsProducer producer;

  public void processMessage(String message) throws JsonProcessingException {
    ScoreServiceRequest request = mapper.readValue(message, ScoreServiceRequest.class);
    if (isNeedCalculateScoreModel(request)) {
      String scoreModelResp = "";
      Long scoreRequestId = scoreRepository.saveScoreRequest(request.getApplicationId(), message);
      String scoreModel = bitbucketService.getScoreFile(request);
      if (!StringUtils.isBlank(scoreModel)) {
        scoreModelResp = dataService.getScoreModelResponse(request, scoreModel);
      }
      scoreRepository.saveScoreResponse(scoreRequestId, request, scoreModelResp);
      producer.sendResponseToQueue(scoreModelResp);
    } else {
      log.error("");
    }
  }

  private boolean isNeedCalculateScoreModel(ScoreServiceRequest request) {
    return request != null && request.getApplicationId() != null && request.getProduct() != null && request.getNodeId() != null;
  }
}
