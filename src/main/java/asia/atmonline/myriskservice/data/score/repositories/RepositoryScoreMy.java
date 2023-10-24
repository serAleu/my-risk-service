package asia.atmonline.myriskservice.data.score.repositories;

import static asia.atmonline.myriskservice.enums.application.ProductCode.IL_Start_RPH;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.enums.application.ProductCode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
//@RequiredArgsConstructor
@Slf4j
public class RepositoryScoreMy {

  @Qualifier("namedParameterJdbcTemplateMy")
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplateMy;

  public RepositoryScoreMy(ApplicationContext context) {
    namedParameterJdbcTemplateMy = context.getBean(NamedParameterJdbcTemplate.class);
  }

  public String executeScoreSqlQuery(String scoreModel, RiskRequestJpaEntity request, ProductCode code) {
    List<String> scoreFilesList;
    if (IL_Start_RPH.equals(code)) {
      Map<String, Object> map = new HashMap<>();
      map.put("applicationId", request.getApplicationId());
      map.put("nodeId", request.getScoreNodeId());
      scoreFilesList = namedParameterJdbcTemplateMy.query(scoreModel, map, (rs, rowNum) -> rs.getString(1));
    } else {
      scoreFilesList = namedParameterJdbcTemplateMy.query(scoreModel,
          new MapSqlParameterSource("applicationId", request.getApplicationId()), (rs, rowNum) -> rs.getString(1));
    }
    return scoreFilesList.get(0);
  }
}