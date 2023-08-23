package asia.atmonline.myriskservice.rules.score.call2;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.SCORECALL2;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.impl.ScoreResponseJpaEntity;
import asia.atmonline.myriskservice.producers.score.ScoreSqsProducer;
import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ScoreCall2Rule extends BaseScoreRule<ScoreCall2Context> {

  public ScoreCall2Rule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<ScoreSqsProducer> execute(ScoreCall2Context context) {
    RiskResponseJpaEntity<ScoreSqsProducer> response = super.execute(context);
    if(context.getScoreNodeId() == 2 && 0 == context.getDecision()) {
      response.setDecision(REJECT);
      response.setRejection_reason_code(SCORECALL2);
    }
    return response;
  }

  @Override
  public ScoreCall2Context getContext(ScoreResponseJpaEntity response, Map<String, Long> score3RestrictionsMap) {
    return new ScoreCall2Context(response.getDecision(), response.getScoreNodeId());
  }
}
