package asia.atmonline.myriskservice.data.score;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.ScoreResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.external_responses.ScoreResponseJpaRepository;
import asia.atmonline.myriskservice.data.score.repositories.RepositoryScoreMy;
import asia.atmonline.myriskservice.enums.application.ProductCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataScoreService {

  private final RepositoryScoreMy repositoryScoreMy;
  private final ScoreResponseJpaRepository scoreResponseJpaRepository;
  private final ObjectMapper mapper;

  public ScoreResponseRiskJpaEntity getScoreModelResponse(RiskRequestRiskJpaEntity request, String scoreModel, ProductCode code) {
    try {
      String scoreModelResponse = repositoryScoreMy.executeScoreSqlQuery(scoreModel, request, code);
      ScoreResponseRiskJpaEntity scoreResponseJpaEntity = mapper.readValue(scoreModelResponse, ScoreResponseRiskJpaEntity.class);
      return scoreResponseJpaRepository.save(scoreResponseJpaEntity);
    } catch (Exception e) {
      log.error("Error while score model executing. credit_application_id = " + request.getApplicationId() + " " + e.getMessage());
      return null;
    }
  }
}