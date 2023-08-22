package asia.atmonline.myriskservice.data.score;

import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.ScoreResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.risk.responses.ScoreResponseJpaRepository;
import asia.atmonline.myriskservice.data.score.repositories.RepositoryScoreMy;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
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
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final ObjectMapper mapper;

  public ScoreResponseJpaEntity getScoreModelResponse(RiskRequestJpaEntity request, String scoreModel, ProductCode code) {
    try {
      String scoreModelResponse = repositoryScoreMy.executeScoreSqlQuery(scoreModel, request, code);
      ScoreResponseJpaEntity scoreResponseJpaEntity = mapper.readValue(scoreModelResponse, ScoreResponseJpaEntity.class);
      return scoreResponseJpaRepository.save(scoreResponseJpaEntity);
    } catch (Exception e) {
      log.error("Error while score model executing. credit_application_id = " + request.getCreditApplicationId() + " " + e.getMessage());
      return null;
    }
  }
}