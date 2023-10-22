package asia.atmonline.myriskservice.data.score;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.score.ScoreResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.repositories.external_responses.score.ScoreResponseJpaRepository;
import asia.atmonline.myriskservice.data.score.repositories.RepositoryScoreMy;
import asia.atmonline.myriskservice.enums.application.ProductCode;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataScoreService {

  private final RepositoryScoreMy repositoryScoreMy;
  private final ScoreResponseJpaRepository scoreResponseJpaRepository;

  public ScoreResponseRiskJpaEntity getScoreModelResponse(RiskRequestJpaEntity request, String scoreModel, ProductCode code) {
    try {
      request.setScoreNodeId(request.getScoreNodeId());
      String scoreModelResponse = repositoryScoreMy.executeScoreSqlQuery(scoreModel, request, code);
      ScoreResponseRiskJpaEntity scoreResponseJpaEntity = rootMapper().readValue(scoreModelResponse, ScoreResponseRiskJpaEntity.class);
      scoreResponseJpaEntity.setScore_node_id(request.getScoreNodeId().intValue());
      return scoreResponseJpaRepository.save(scoreResponseJpaEntity);
    } catch (Exception e) {
      log.error("Error while score model executing. credit_application_id = " + request.getApplicationId() + " " + e.getMessage());
      return null;
    }
  }

  private ObjectMapper rootMapper() {
    ObjectMapper featuredMapper = new ObjectMapper();
    featuredMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    featuredMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    featuredMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return featuredMapper;
  }
}