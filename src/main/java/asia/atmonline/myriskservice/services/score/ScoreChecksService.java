package asia.atmonline.myriskservice.services.score;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.risk.entity.RiskRequestRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.ScoreResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.score.DataScoreService;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.property.impl.SystemProperty;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.property.SystemPropertyJpaRepository;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreChecksService implements BaseRiskChecksService {

  private final List<? extends BaseScoreRule<? extends BaseScoreContext>> rules;
  private final DataScoreService dataScoreService;
  private final BitbucketService bitbucketService;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final SystemPropertyJpaRepository systemPropertyJpaRepository;

  @Value("${score.paths-to-properties.term.max}")
  private String scorePathTermMax;
  @Value("${score.paths-to-properties.term.min}")
  private String scorePathTermMin;
  @Value("${score.paths-to-properties.amount.max}")
  private String scorePathAmountMax;
  @Value("${score.paths-to-properties.amount.min}")
  private String scorePathAmountMin;

  @Override
  public RiskResponseRiskJpaEntity process(RiskRequestRiskJpaEntity request) {
    RiskResponseRiskJpaEntity response = new RiskResponseRiskJpaEntity();
    ScoreResponseRiskJpaEntity scoreResponseJpaEntity = new ScoreResponseRiskJpaEntity().setCreditApplicationId(request.getApplicationId());
    Optional<CreditApplication> application = creditApplicationJpaRepository.findById(request.getApplicationId());
    if(application.isPresent()) {
      ProductCode code = ProductCode.getProductByCode(application.get().getCreditProductId());
      String scoreModel = bitbucketService.getModel(code);
      if (!StringUtils.isBlank(scoreModel)) {
        scoreResponseJpaEntity = dataScoreService.getScoreModelResponse(request, scoreModel, code);
      }
      Map<String, Long> score3RestrictionsMap = getScoreLimitAndDecisionRestrictions(request);
      for (BaseScoreRule rule : rules) {
        response = rule.execute(
            rule.getContext(scoreResponseJpaEntity, score3RestrictionsMap));
        if (response != null && REJECT.equals(response.getDecision())) {
          if (response.getRejectionReason() != null) {
            rule.saveToBlacklists(application.get().getBorrower().getId(), response.getRejectionReason());
          }
          return response;
        }
      }
    }
    return response;
  }

  private Map<String, Long> getScoreLimitAndDecisionRestrictions(RiskRequestRiskJpaEntity request) {
    Map<String, Long> map = new HashMap<>();
    if(3 == request.getScoreNodeId()) {
//      Optional<SystemProperty> termMaxProperty = systemPropertyJpaRepository.findByPropertyKey(scorePathTermMax);
      Optional<SystemProperty> termMaxProperty = null;
      if(termMaxProperty.isPresent() && termMaxProperty.get().getValue() != null) {
        map.put(scorePathTermMax, Long.parseLong(termMaxProperty.get().getValue()));
      }
//      Optional<SystemProperty> amountMaxProperty = systemPropertyJpaRepository.findByPropertyKey(scorePathAmountMax);
      Optional<SystemProperty> amountMaxProperty = null;
      if(amountMaxProperty.isPresent() && amountMaxProperty.get().getValue() != null) {
        map.put(scorePathAmountMax, Long.parseLong(amountMaxProperty.get().getValue()));
      }
//      Optional<SystemProperty> termMinProperty = systemPropertyJpaRepository.findByPropertyKey(scorePathTermMin);
      Optional<SystemProperty> termMinProperty = null;
      if(termMinProperty.isPresent() && termMinProperty.get().getValue() != null) {
        map.put(scorePathTermMin, Long.parseLong(termMinProperty.get().getValue()));
      }
//      Optional<SystemProperty> amountMinProperty = systemPropertyJpaRepository.findByPropertyKey(scorePathAmountMin);
      Optional<SystemProperty> amountMinProperty = null;
      if(amountMinProperty.isPresent() && amountMinProperty.get().getValue() != null) {
        map.put(scorePathAmountMin, Long.parseLong(amountMinProperty.get().getValue()));
      }
    }
    return map;
  }
}
