package asia.atmonline.myriskservice.web.score.bitbucket;

import asia.atmonline.myriskservice.enums.application.ProductCode;
import asia.atmonline.myriskservice.messages.request.impl.ScoreRequest;
import asia.atmonline.myriskservice.web.score.ScoreCacheExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BitbucketService {

  private final BitbucketClient bitbucketClient;

  public String getScoreFile(ScoreRequest request) {
    return getModel(request.getProduct());
  }

  private String getModel(ProductCode productCode) {
    boolean needUpdateCache = ScoreCacheExecutor.isCachedModelLastUpdDtmAfterReloadTimeMinutes(productCode);
    String scoreModel = "";
    switch (productCode) {
      case IL -> {
        if (needUpdateCache || StringUtils.isBlank(ScoreCacheExecutor.ilScoreModel)) {
          ScoreCacheExecutor.ilScoreModel = bitbucketClient.getBitbucketIlModel();
        }
        scoreModel = ScoreCacheExecutor.ilScoreModel;
      }
      case RS1 -> {
        if (needUpdateCache || StringUtils.isBlank(ScoreCacheExecutor.rs1ScoreModel)) {
          ScoreCacheExecutor.rs1ScoreModel = bitbucketClient.getBitbucketRs1Model();
        }
        scoreModel = ScoreCacheExecutor.rs1ScoreModel;
      }
      case RS2 -> {
        if (needUpdateCache || StringUtils.isBlank(ScoreCacheExecutor.rs2ScoreModel)) {
          ScoreCacheExecutor.rs2ScoreModel = bitbucketClient.getBitbucketRs2Model();
        }
        scoreModel = ScoreCacheExecutor.rs2ScoreModel;
      }
      case RS3, RS4, RS5, RS6, RS7 -> {
        if (needUpdateCache || StringUtils.isBlank(ScoreCacheExecutor.rs3PlusScoreModel)) {
          ScoreCacheExecutor.rs3PlusScoreModel = bitbucketClient.getBitbucketRs3PlusModel();
        }
        scoreModel = ScoreCacheExecutor.rs3PlusScoreModel;
      }
    }
    return scoreModel;
  }
}