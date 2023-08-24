//package asia.atmonline.myriskservice.rules.score.call1err;
//
//import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
//import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.SCORECALL1ERR;
//
//import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
//import asia.atmonline.myriskservice.data.entity.risk.responses.impl.ScoreResponseJpaEntity;
//import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
//import asia.atmonline.myriskservice.rules.score.BaseScoreContext;
//import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
//import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
//import java.util.Map;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ScoreCall1ErrRule extends BaseScoreRule<ScoreCall1ErrContext> {
//
//  public ScoreCall1ErrRule(BlacklistChecksService blacklistChecksService) {
//    super(blacklistChecksService);
//  }
//
//  @Override
//  public RiskResponseJpaEntity<ScoreSqsProducer> execute(ScoreCall1ErrContext context) {
//    RiskResponseJpaEntity<ScoreSqsProducer> response = super.execute(context);
//    if (context.getScoreNodeId() == 1 && (context.getDecision() == null ||
//        !BaseScoreContext.DECISION_POSSIBLE_VALUES_LIST.contains(context.getDecision()))) {
//      response.setDecision(REJECT);
//      response.setRejection_reason_code(SCORECALL1ERR);
//    }
//    return response;
//  }
//
//  @Override
//  public ScoreCall1ErrContext getContext(ScoreResponseJpaEntity response, Map<String, Long> score3RestrictionsMap) {
//    return new ScoreCall1ErrContext(response.getDecision(), response.getScoreNodeId());
//  }
//}
