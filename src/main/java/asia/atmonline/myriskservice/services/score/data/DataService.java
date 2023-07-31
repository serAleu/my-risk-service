package asia.atmonline.myriskservice.services.score.data;

import asia.atmonline.myriskservice.messages.request.impl.ScoreServiceRequest;
import asia.atmonline.myriskservice.services.score.data.repositories.RepositoryMy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataService {

  private final RepositoryMy repositoryMy;

  public String getScoreModelResponse(ScoreServiceRequest request, String scoreModel) {
    String scoreModelResponse = createEmptyScoreModelResponse(request);
    try {
      scoreModelResponse = repositoryMy.executeScoreSqlQuery(scoreModel, request);
    } catch (Exception e) {
      log.error("");
    }
    return scoreModelResponse;
  }

  private String createEmptyScoreModelResponse(ScoreServiceRequest request) {
    return "{\"scoring\": {\"application_id\": " + request.getAppId() + "}}";
  }
}