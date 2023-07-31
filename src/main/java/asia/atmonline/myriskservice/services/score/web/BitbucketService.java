package asia.atmonline.myriskservice.services.score.web;

import asia.atmonline.myriskservice.enums.ProductCode;
import asia.atmonline.myriskservice.messages.request.impl.ScoreServiceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BitbucketService {

  private final BitbucketClient bitbucketClient;

  public String getScoreFile(ScoreServiceRequest request) {
    return getModel(request.getProduct());
  }

  private String getModel(ProductCode productCode) {
    boolean needUpdateCache = Cache.checkModLastUpdDtm(productCode);
    String scoreModel = "";
    switch (productCode) {
      case IL -> {
        if (needUpdateCache || StringUtils.isBlank(Cache.ilScoreModel)) {
          Cache.ilScoreModel = bitbucketClient.getBitbucketIlModel();
        }
        scoreModel = Cache.ilScoreModel;
      }
      case RS1 -> {
        if (needUpdateCache || StringUtils.isBlank(Cache.rs1ScoreModel)) {
          Cache.rs1ScoreModel = bitbucketClient.getBitbucketRs1Model();
        }
        scoreModel = Cache.rs1ScoreModel;
      }
      case RS2 -> {
        if (needUpdateCache || StringUtils.isBlank(Cache.rs2ScoreModel)) {
          Cache.rs2ScoreModel = bitbucketClient.getBitbucketRs2Model();
        }
        scoreModel = Cache.rs2ScoreModel;
      }
      case RS3, RS4, RS5, RS6, RS7 -> {
        if (needUpdateCache || StringUtils.isBlank(Cache.rs3PlusScoreModel)) {
          Cache.rs3PlusScoreModel = bitbucketClient.getBitbucketRs3PlusModel();
        }
        scoreModel = Cache.rs3PlusScoreModel;
      }
    }
    return scoreModel;
  }
}