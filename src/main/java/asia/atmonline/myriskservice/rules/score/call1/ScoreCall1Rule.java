//package asia.atmonline.myriskservice.rules.score.call1;
//
//import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
//import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.SCORECALL1;
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
//public class ScoreCall1Rule extends BaseScoreRule<ScoreCall1Context> {
//
//  public ScoreCall1Rule(BlacklistChecksService blacklistChecksService) {
//    super(blacklistChecksService);
//  }
//
//  @Override
//  public RiskResponseJpaEntity<ScoreSqsProducer> execute(ScoreCall1Context context) {
//    RiskResponseJpaEntity<ScoreSqsProducer> response = super.execute(context);
//    if(context.getScoreNodeId() == 1 && 0 == context.getDecision()) {
//      response.setDecision(REJECT);
//      response.setRejection_reason_code(SCORECALL1);
//    }
//    return response;
//  }
//
//  @Override
//  public ScoreCall1Context getContext(ScoreResponseJpaEntity response, Map<String, Long> score3RestrictionsMap) {
//    return new ScoreCall1Context(response.getDecision(), response.getScoreNodeId());
//  }
//}
