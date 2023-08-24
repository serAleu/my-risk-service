//package asia.atmonline.myriskservice.rules.score.call3err;
//
//import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
//import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.SCORECALL3ERR;
//
//import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
//import asia.atmonline.myriskservice.data.entity.risk.responses.impl.ScoreResponseJpaEntity;
//import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
//import asia.atmonline.myriskservice.rules.score.BaseScoreContext;
//import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
//import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ScoreCall3ErrRule extends BaseScoreRule<ScoreCall3ErrContext> {
//
//  @Value("${score.paths-to-properties.term.max}")
//  private String scorePathTermMax;
//  @Value("${score.paths-to-properties.term.min}")
//  private String scorePathTermMin;
//  @Value("${score.paths-to-properties.amount.max}")
//  private String scorePathAmountMax;
//  @Value("${score.paths-to-properties.amount.min}")
//  private String scorePathAmountMin;
//
//  public ScoreCall3ErrRule(BlacklistChecksService blacklistChecksService) {
//    super(blacklistChecksService);
//  }
//
//  @Override
//  public RiskResponseJpaEntity<ScoreSqsProducer> execute(ScoreCall3ErrContext context) {
//    RiskResponseJpaEntity<ScoreSqsProducer> response = super.execute(context);
//    if (context.getScoreNodeId() == 3 && (context.getDecision() == null
//        || !BaseScoreContext.DECISION_POSSIBLE_VALUES_LIST.contains(context.getDecision())
//        || context.getTermFromScore() == null
//        || context.getLimitFromScore() == null
//        || context.getLimitFromScore() > context.getAmountMaxRestriction()
//        || context.getLimitFromScore() < context.getAmountMinRestriction()
//        || context.getTermFromScore() > context.getTermMaxRestriction()
//        || context.getTermFromScore() < context.getTermMinRestriction())) {
//      response.setDecision(REJECT);
//      response.setRejection_reason_code(SCORECALL3ERR);
//    }
//    return response;
//  }
//
//  @Override
//  public ScoreCall3ErrContext getContext(ScoreResponseJpaEntity response, Map<String, Long> score3RestrictionsMap) {
//    return new ScoreCall3ErrContext(response.getDecision(), response.getScoreNodeId(), score3RestrictionsMap.get(scorePathTermMax),
//        score3RestrictionsMap.get(scorePathAmountMax), score3RestrictionsMap.get(scorePathTermMin), score3RestrictionsMap.get(scorePathAmountMin),
//        response.getTerm(), response.getLimit());
//  }
//}
