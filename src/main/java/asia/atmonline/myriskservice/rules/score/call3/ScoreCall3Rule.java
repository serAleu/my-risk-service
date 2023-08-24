//package asia.atmonline.myriskservice.rules.score.call3;
//
//import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
//import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.SCORECALL3;
//
//import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
//import asia.atmonline.myriskservice.data.entity.risk.responses.impl.ScoreResponseJpaEntity;
//import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
//import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
//import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
//import java.util.Map;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ScoreCall3Rule extends BaseScoreRule<ScoreCall3Context> {
//
//  public ScoreCall3Rule(BlacklistChecksService blacklistChecksService) {
//    super(blacklistChecksService);
//  }
//
//  @Override
//  public RiskResponseJpaEntity<ScoreSqsProducer> execute(ScoreCall3Context context) {
//    RiskResponseJpaEntity<ScoreSqsProducer> response = super.execute(context);
//    if(context.getScoreNodeId() == 3 && 0 == context.getDecision()) {
//      response.setDecision(REJECT);
//      response.setRejection_reason_code(SCORECALL3);
//    }
//    return response;
//  }
//
//  @Override
//  public ScoreCall3Context getContext(ScoreResponseJpaEntity response, Map<String, Long> score3RestrictionsMap) {
//    return new ScoreCall3Context(response.getDecision(), response.getScoreNodeId());
//  }
//}
