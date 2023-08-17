package asia.atmonline.myriskservice.data.score.repositories;

import static asia.atmonline.myriskservice.enums.application.ProductCode.IL;

import asia.atmonline.myriskservice.messages.request.impl.ScoreRequest;
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
    System.out.println();
  }

  public String executeScoreSqlQuery(String request, ScoreRequest scoreRequest) {
    List<String> scoreFilesList;
    if (scoreRequest.getProduct() == IL) {
      Map<String, Object> map = new HashMap<>();
      map.put("creditApplicationId", scoreRequest.getCreditApplicationId());
      map.put("nodeId", scoreRequest.getNodeId());
      scoreFilesList = namedParameterJdbcTemplateMy.query(request, map, (rs, rowNum) -> rs.getString(1));
    } else {
      scoreFilesList = namedParameterJdbcTemplateMy.query(request,
          new MapSqlParameterSource("creditApplicationId", scoreRequest.getCreditApplicationId()), (rs, rowNum) -> rs.getString(1));
    }
    return scoreFilesList.get(0);
  }
}