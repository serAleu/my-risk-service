//package asia.atmonline.myriskservice.rules.score;
//
//import static asia.atmonline.myriskservice.enums.risk.CheckType.SCORE;
//
//import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
//import asia.atmonline.myriskservice.data.entity.risk.responses.impl.ScoreResponseJpaEntity;
//import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
//import asia.atmonline.myriskservice.rules.BaseRule;
//import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
//import java.util.Map;
//
//public abstract class BaseScoreRule<P extends BaseScoreContext> extends BaseRule<P> {
//
//  public BaseScoreRule(BlacklistChecksService blacklistChecksService) {
//    super(blacklistChecksService);
//  }
//
//  @Override
//  @SuppressWarnings({"unchecked"})
//  public RiskResponseJpaEntity<ScoreSqsProducer> execute(P context) {
//    return (RiskResponseJpaEntity<ScoreSqsProducer>) getApprovedResponse(context.getRiskResponseJpaEntity().getCredit_application_id(), SCORE,
//        context.getRiskResponseJpaEntity());
//  }
//
//  public abstract P getContext(ScoreResponseJpaEntity response, Map<String, Long> score3RestrictionsMap);
//}
