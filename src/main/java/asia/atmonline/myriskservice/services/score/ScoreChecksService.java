package asia.atmonline.myriskservice.services.score;

import static asia.atmonline.myriskservice.enums.risk.CheckType.SCORE;
import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.RiskRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.ScoreResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.score.DataScoreService;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.property.SystemProperty;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.property.SystemPropertyJpaRepository;
import asia.atmonline.myriskservice.enums.application.ProductCode;
import asia.atmonline.myriskservice.producers.BaseSqsProducer;
import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
import asia.atmonline.myriskservice.rules.score.BaseScoreContext;
import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
import asia.atmonline.myriskservice.services.BaseChecksService;
import asia.atmonline.myriskservice.web.score.bitbucket.BitbucketService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ScoreChecksService extends BaseChecksService {

  private final List<? extends BaseScoreRule<? extends BaseScoreContext>> rules;
  private final DataScoreService dataScoreService;
  private final BitbucketService bitbucketService;
  private final CreditApplicationJpaRepository creditApplicationJpaRepository;
  private final SystemPropertyJpaRepository systemPropertyJpaRepository;
  private final ScoreSqsProducer producer;

  @Value("${score.paths-to-properties.term.max}")
  private String scorePathTermMax;
  @Value("${score.paths-to-properties.term.min}")
  private String scorePathTermMin;
  @Value("${score.paths-to-properties.amount.max}")
  private String scorePathAmountMax;
  @Value("${score.paths-to-properties.amount.min}")
  private String scorePathAmountMin;

  public ScoreChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories,
      List<? extends BaseScoreRule<? extends BaseScoreContext>> rules, DataScoreService dataScoreService, BitbucketService bitbucketService,
      CreditApplicationJpaRepository creditApplicationJpaRepository, SystemPropertyJpaRepository systemPropertyJpaRepository,
      ScoreSqsProducer producer) {
    super(repositories);
    this.rules = rules;
    this.dataScoreService = dataScoreService;
    this.bitbucketService = bitbucketService;
    this.creditApplicationJpaRepository = creditApplicationJpaRepository;
    this.systemPropertyJpaRepository = systemPropertyJpaRepository;
    this.producer = producer;
  }

  @SuppressWarnings({"unchecked"})
  public ScoreSqsProducer getProducer() {
    return producer;
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity<? extends BaseSqsProducer> process(RiskRequestJpaEntity request) {
    RiskResponseJpaEntity<ScoreSqsProducer> response = new RiskResponseJpaEntity<>();
    ScoreResponseJpaEntity scoreResponseJpaEntity = new ScoreResponseJpaEntity().setCreditApplicationId(request.getCreditApplicationId());
    Optional<CreditApplication> application = creditApplicationJpaRepository.findById(request.getCreditApplicationId());
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
          if (response.getRejection_reason_code() != null) {
            rule.saveToBlacklists(application.get().getBorrower().getId(), response.getRejection_reason_code());

          }
          return response;
        }
      }
    }
    return response;
  }

  @Override
  public boolean accept(RiskRequestJpaEntity request) {
    return request != null && SCORE.equals(request.getCheckType()) && request.getCreditApplicationId() != null && request.getScoreNodeId() != null;
  }

  private Map<String, Long> getScoreLimitAndDecisionRestrictions(RiskRequestJpaEntity request) {
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
