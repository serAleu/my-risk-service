package asia.atmonline.myriskservice.services.score.data.repositories;

import static asia.atmonline.myriskservice.enums.ProductCode.IL;

import asia.atmonline.myriskservice.messages.request.impl.ScoreServiceRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class RepositoryMy {

  @Autowired
  @Qualifier("namedParameterJdbcTemplateMy")
  private NamedParameterJdbcTemplate namedParameterJdbcTemplateMy;

  public String executeScoreSqlQuery(String request, ScoreServiceRequest scoreServiceRequest) {
    List<String> scoreFilesList;
    if (scoreServiceRequest.getProduct() == IL) {
      Map<String, Object> map = new HashMap<>();
      map.put("applicationId", scoreServiceRequest.getApplicationId());
      map.put("nodeId", scoreServiceRequest.getNodeId());
      scoreFilesList = namedParameterJdbcTemplateMy.query(request, map, (rs, rowNum) -> rs.getString(1));
    } else {
      scoreFilesList = namedParameterJdbcTemplateMy.query(request,
              new MapSqlParameterSource("applicationId", scoreServiceRequest.getApplicationId()), (rs, rowNum) -> rs.getString(1));
    }
    return scoreFilesList.get(0);
  }
}