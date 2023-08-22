package asia.atmonline.myriskservice.services.score;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;

import asia.atmonline.myriskservice.data.entity.BaseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.requests.impl.ScoreRequestJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.ScoreResponseJpaEntity;
import asia.atmonline.myriskservice.data.repositories.impl.BaseJpaRepository;
import asia.atmonline.myriskservice.data.score.DataScoreService;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.data.storage.entity.property.SystemProperty;
import asia.atmonline.myriskservice.data.storage.repositories.application.CreditApplicationJpaRepository;
import asia.atmonline.myriskservice.data.storage.repositories.property.SystemPropertyJpaRepository;
import asia.atmonline.myriskservice.messages.request.impl.ScoreRequest;
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
public class ScoreChecksService extends BaseChecksService<ScoreRequest, ScoreRequestJpaEntity> {

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

  public ScoreChecksService(Map<String, ? extends BaseJpaRepository<? extends BaseJpaEntity>> repositories,
      List<? extends BaseScoreRule<? extends BaseScoreContext>> rules, DataScoreService dataScoreService, BitbucketService bitbucketService,
      CreditApplicationJpaRepository creditApplicationJpaRepository, SystemPropertyJpaRepository systemPropertyJpaRepository) {
    super(repositories);
    this.rules = rules;
    this.dataScoreService = dataScoreService;
    this.bitbucketService = bitbucketService;
    this.creditApplicationJpaRepository = creditApplicationJpaRepository;
    this.systemPropertyJpaRepository = systemPropertyJpaRepository;
  }

  @Override
  @SuppressWarnings({"unchecked", "rawtypes"})
  public RiskResponseJpaEntity<? extends BaseSqsProducer> process(ScoreRequest request) {
    RiskResponseJpaEntity<ScoreSqsProducer> response = new RiskResponseJpaEntity<>();
    ScoreResponseJpaEntity scoreResponseJpaEntity = new ScoreResponseJpaEntity().setCreditApplicationId(request.getCreditApplicationId());
    String scoreModel = bitbucketService.getScoreFile(request);
    if (!StringUtils.isBlank(scoreModel)) {
      scoreResponseJpaEntity = dataScoreService.getScoreModelResponse(request, scoreModel);
    }
    Map<String, Long> score3RestrictionsMap = getScoreLimitAndDecisionRestrictions(request);
    for (BaseScoreRule rule : rules) {
      response = rule.execute(
          rule.getContext(scoreResponseJpaEntity, score3RestrictionsMap));
      if (response != null && REJECT.equals(response.getDecision())) {
        if (response.getRejectionReasonCode() != null) {
          Optional<CreditApplication> application = creditApplicationJpaRepository.findById(request.getCreditApplicationId());
          if (application.isPresent() && application.get().getBorrower() != null) {
            rule.saveToBlacklists(application.get().getBorrower().getId(), response.getRejectionReasonCode());
          }
        }
        return response;
      }
    }
    return response;
  }

  @Override
  public boolean accept(ScoreRequest request) {
    return request != null && request.getCreditApplicationId() != null && request.getProduct() != null && request.getNodeId() != null;
  }

  @Override
  public ScoreRequestJpaEntity getRequestEntity(ScoreRequest request) {
    return new ScoreRequestJpaEntity().setScoreNodeId(request.getNodeId())
        .setProduct(request.getProduct())
        .setCreditApplicationId(request.getCreditApplicationId());
  }

  private Map<String, Long> getScoreLimitAndDecisionRestrictions(ScoreRequest request) {
    Map<String, Long> map = new HashMap<>();
    if(3 == request.getNodeId()) {
      Optional<SystemProperty> termMaxProperty = systemPropertyJpaRepository.findByPropertyKey(scorePathTermMax);
      if(termMaxProperty.isPresent() && termMaxProperty.get().getTerm() != null) {
        map.put(scorePathTermMax, termMaxProperty.get().getTerm());
      }
      Optional<SystemProperty> amountMaxProperty = systemPropertyJpaRepository.findByPropertyKey(scorePathAmountMax);
      if(amountMaxProperty.isPresent() && amountMaxProperty.get().getAmount() != null) {
        map.put(scorePathAmountMax, amountMaxProperty.get().getAmount());
      }
      Optional<SystemProperty> termMinProperty = systemPropertyJpaRepository.findByPropertyKey(scorePathTermMin);
      if(termMinProperty.isPresent() && termMinProperty.get().getTerm() != null) {
        map.put(scorePathTermMin, termMinProperty.get().getTerm());
      }
      Optional<SystemProperty> amountMinProperty = systemPropertyJpaRepository.findByPropertyKey(scorePathAmountMin);
      if(amountMinProperty.isPresent() && amountMinProperty.get().getAmount() != null) {
        map.put(scorePathAmountMin, amountMinProperty.get().getAmount());
      }
    }
    return map;
  }
}
