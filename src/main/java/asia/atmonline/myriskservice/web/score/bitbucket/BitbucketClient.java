package asia.atmonline.myriskservice.web.score.bitbucket;

import asia.atmonline.myriskservice.web.score.ScoreCacheExecutor;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class BitbucketClient {

  @Autowired
  @Qualifier("restTemplate")
  private RestTemplate restTemplate;
  @Autowired
  @Qualifier("httpEntity")
  private HttpEntity<?> httpEntity;

  @Value("${score.bitbucket.url.base}")
  private String bitbucketUrlPath;
  @Value("${score.bitbucket.url.il}")
  private String bitbucketUrlIlModel;
  @Value("${score.bitbucket.url.rs1}")
  private String bitbucketUrlRs1Model;
  @Value("${score.bitbucket.url.rs2}")
  private String bitbucketUrlRs2Model;
  @Value("${score.bitbucket.url.rs3}")
  private String bitbucketUrlRs3PlusModel;

  public String getBitbucketIlModel() {
    return requestToBitbucket(bitbucketUrlPath + bitbucketUrlIlModel, ScoreCacheExecutor.ilScoreModel);
  }

  public String getBitbucketRs1Model() {
    return requestToBitbucket(bitbucketUrlPath + bitbucketUrlRs1Model, ScoreCacheExecutor.rs1ScoreModel);
  }

  public String getBitbucketRs2Model() {
    return requestToBitbucket(bitbucketUrlPath + bitbucketUrlRs2Model, ScoreCacheExecutor.rs2ScoreModel);
  }

  public String getBitbucketRs3PlusModel() {
    return requestToBitbucket(bitbucketUrlPath + bitbucketUrlRs3PlusModel, ScoreCacheExecutor.rs3PlusScoreModel);
  }

  private String requestToBitbucket(String urlString, String currentModelVersion) {
    try {
      URL url = new URL(urlString);
      ResponseEntity<String> responseJson = restTemplate.exchange(url.toURI(), HttpMethod.GET, httpEntity, String.class);
      return responseJson.getBody();
    } catch (Exception e) {
      log.error("Error while getting score model from bitbucket. " + e.getMessage());
      return !StringUtils.isBlank(currentModelVersion) ? currentModelVersion : "";
    }
  }
}
