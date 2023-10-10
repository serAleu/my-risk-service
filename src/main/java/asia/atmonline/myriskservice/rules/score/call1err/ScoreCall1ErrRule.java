package asia.atmonline.myriskservice.rules.score.call1err;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.SCORECALL1ERR;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.score.ScoreResponseRiskJpaEntity;
import asia.atmonline.myriskservice.rules.score.BaseScoreContext;
import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ScoreCall1ErrRule extends BaseScoreRule<ScoreCall1ErrContext> {

  public ScoreCall1ErrRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(ScoreCall1ErrContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if (context.getScoreNodeId() == 1 && (context.getDecision() == null ||
        !BaseScoreContext.DECISION_POSSIBLE_VALUES_LIST.contains(context.getDecision()))) {
      response.setDecision(REJECT);
      response.setRejectionReason(SCORECALL1ERR);
    }
    return response;
  }

  @Override
  public ScoreCall1ErrContext getContext(RiskResponseJpaEntity response, ScoreResponseRiskJpaEntity scoreResponse, Map<String, Long> score3RestrictionsMap) {
    return new ScoreCall1ErrContext(response, scoreResponse.getDecision(), scoreResponse.getScore_node_id());
  }
}
