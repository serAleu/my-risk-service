package asia.atmonline.myriskservice.rules.score.call1;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.SCORECALL1;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.score.ScoreResponseRiskJpaEntity;
import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ScoreCall1Rule extends BaseScoreRule<ScoreCall1Context> {

  public ScoreCall1Rule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(ScoreCall1Context context) {
    RiskResponseJpaEntity response = super.execute(context);
    if(context.getScoreNodeId() == 1 && 0 == context.getDecision()) {
      response.setDecision(REJECT);
      response.setRejectionReason(SCORECALL1);
    }
    return response;
  }

  @Override
  public ScoreCall1Context getContext(RiskResponseJpaEntity response, ScoreResponseRiskJpaEntity scoreResponse, Map<String, Long> score3RestrictionsMap) {
    return new ScoreCall1Context(response, scoreResponse.getDecision(), scoreResponse.getScore_node_id());
  }
}
