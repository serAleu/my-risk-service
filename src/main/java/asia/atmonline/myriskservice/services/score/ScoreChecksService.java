package asia.atmonline.myriskservice.services.score;

import static asia.atmonline.myriskservice.enums.risk.CheckType.SCORE;
import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.score.ScoreResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.score.DataScoreService;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.credit.CreditProduct;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.credit.CreditProductJpaRepository;
import asia.atmonline.myriskservice.enums.application.ProductCode;
import asia.atmonline.myriskservice.rules.score.BaseScoreContext;
import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
import asia.atmonline.myriskservice.services.BaseRiskChecksService;
import asia.atmonline.myriskservice.web.score.bitbucket.BitbucketService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreChecksService implements BaseRiskChecksService {

  private final List<? extends BaseScoreRule<? extends BaseScoreContext>> rules;
  private final DataScoreService dataScoreService;
  private final BitbucketService bitbucketService;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final CreditProductJpaRepository creditProductJpaRepository;

  @Value("${score.paths-to-properties.term.max}")
  private String scorePathTermMax;
  @Value("${score.paths-to-properties.term.min}")
  private String scorePathTermMin;
  @Value("${score.paths-to-properties.amount.max}")
  private String scorePathAmountMax;
  @Value("${score.paths-to-properties.amount.min}")
  private String scorePathAmountMin;

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = getRiskResponseJpaEntity(request);
    ScoreResponseRiskJpaEntity scoreResponseJpaEntity = new ScoreResponseRiskJpaEntity().setApplication_id(request.getApplicationId());
    Optional<CreditApplication> application = creditApplicationJpaRepository.findById(request.getApplicationId());
    if (application.isPresent()) {
      ProductCode code = ProductCode.getProductByCode(application.get().getCreditProductId());
      String scoreModel = bitbucketService.getModel(code);
      if (!StringUtils.isBlank(scoreModel)) {
        scoreResponseJpaEntity = dataScoreService.getScoreModelResponse(request, scoreModel, code);
      }
      Map<String, Long> score3RestrictionsMap = getScoreLimitAndDecisionRestrictions(request, code);
      for (BaseScoreRule rule : rules) {
        response = rule.execute(rule.getContext(response, scoreResponseJpaEntity, score3RestrictionsMap));
        if (response != null && REJECT.equals(response.getDecision())) {
          if (response.getRejectionReason() != null) {
            rule.saveToBlacklists(request.getApplicationId(), application.get().getBorrower().getId(), response.getRejectionReason());
          }
          return response;
        }
      }
    }
    return response;
  }

  @NotNull
  private RiskResponseJpaEntity getRiskResponseJpaEntity(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity response = new RiskResponseJpaEntity();
    response.setRequestId(request.getId());
    response.setApplicationId(request.getApplicationId());
    response.setCheckType(SCORE);
    return response;
  }

  private Map<String, Long> getScoreLimitAndDecisionRestrictions(RiskRequestJpaEntity request, ProductCode code) {
    Map<String, Long> map = new HashMap<>();
    if (3 == request.getScoreNodeId()) {
      Optional<CreditProduct> creditProduct = creditProductJpaRepository.findById(code.getCodeNum());
      if(creditProduct.isPresent()) {
        if (creditProduct.get().getMaxTerm() != null) {
          map.put(scorePathTermMax, creditProduct.get().getMaxTerm().longValue());
        }
        if (creditProduct.get().getMaxAmount() != null) {
          map.put(scorePathAmountMax, creditProduct.get().getMaxAmount().longValue());
        }
        if (creditProduct.get().getMinTerm() != null) {
          map.put(scorePathTermMin, creditProduct.get().getMinTerm().longValue());
        }
        if (creditProduct.get().getMinAmount() != null) {
          map.put(scorePathAmountMin, creditProduct.get().getMinAmount().longValue());
        }
      }
    }
    return map;
  }
}
