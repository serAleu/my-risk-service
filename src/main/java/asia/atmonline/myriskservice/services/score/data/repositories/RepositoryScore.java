package asia.atmonline.myriskservice.services.score.data.repositories;

import asia.atmonline.myriskservice.messages.request.impl.ScoreServiceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class RepositoryScore {

  @Autowired
  @Qualifier("namedParameterJdbcTemplateMy")
  private NamedParameterJdbcTemplate namedParameterJdbcTemplateMy;

  @Autowired
  private ObjectMapper mapper;
  @Value("${aws.sqs.score.receiver.queue-name}")
  private String scoreRequestQueueName;
  @Value("${aws.sqs.score.receiver.queue-name}")
  private String scoreResponseQueueName;

  private static final String INSERT_INTO_SCORE_REQUEST_TABLE =
      "insert into score_request (credit_application_id,message_body,queue_name) " +
          "values(:creditApplicationId, :messageBody, :queueName) " +
          "returning id";

  private static final String INSERT_INTO_SCORE_RESPONSE_TABLE =
      "insert into score_response (score_request_id,model_id,model_version," +
          "credit_application_id,queue_name,message_body) " +
          "values(:scoreRequestId, :modelId, :modelVersion, :creditApplicationId, :queueName," +
          ":messageBody) ";

  public Long saveScoreRequest(Long creditApplicationId, String message) {
    try {
      Map<String, Object> map = new HashMap<>();
      map.put("creditApplicationId", creditApplicationId);
      map.put("messageBody", message);
      map.put("queueName", scoreRequestQueueName);
      return namedParameterJdbcTemplateMy.queryForObject(INSERT_INTO_SCORE_REQUEST_TABLE,
          map, Long.class);
    } catch (Exception e) {
      log.error("");
      return null;
    }
  }

  public void saveScoreResponse(Long scoreRequestId, ScoreServiceRequest request, String scoreModelResp) {
    try {
      if (scoreRequestId != null) {
        String modelVersion = "";
        String modelId = "";
        try {
          ObjectNode node = mapper.readValue(scoreModelResp, ObjectNode.class);
          modelVersion = node.get("scoring").get("model_version").textValue();
          modelId = node.get("scoring").get("model_id").textValue();
        } catch (JsonProcessingException ignored) {
        }
        Map<String, Object> map = fillMapForUpdate(scoreRequestId, request, scoreModelResp,
            modelVersion, modelId, scoreResponseQueueName);
        namedParameterJdbcTemplateMy.update(INSERT_INTO_SCORE_RESPONSE_TABLE, map);
      }
    } catch (Exception e) {
      log.error("");
    }
  }

  private Map<String, Object> fillMapForUpdate(Long scoreRequestId,
      ScoreServiceRequest request, String scoreModelResp, String modelVersion, String modelId,
      String queueName) {
    Map<String, Object> map = new HashMap<>();
    map.put("scoreRequestId", scoreRequestId);
    map.put("modelId", StringUtils.isBlank(modelId) ? "unknown-model-id" : modelId);
    map.put("modelVersion", StringUtils.isBlank(
        modelVersion) ? "unknown-model-version" : modelVersion);
    map.put("creditApplicationId", request.getCreditApplicationId());
    map.put("queueName", StringUtils.isBlank(queueName) ? "unknown-queue-name" : queueName);
    map.put("messageBody", scoreModelResp);
    return map;
  }
}