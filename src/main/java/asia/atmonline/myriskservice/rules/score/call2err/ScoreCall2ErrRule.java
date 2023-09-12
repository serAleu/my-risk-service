package asia.atmonline.myriskservice.rules.score.call2err;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.SCORECALL2ERR;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.ScoreResponseRiskJpaEntity;
import asia.atmonline.myriskservice.rules.score.BaseScoreContext;
import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ScoreCall2ErrRule extends BaseScoreRule<ScoreCall2ErrContext> {

  public ScoreCall2ErrRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(ScoreCall2ErrContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if (context.getScoreNodeId() == 2 && (context.getDecision() == null ||
        !BaseScoreContext.DECISION_POSSIBLE_VALUES_LIST.contains(context.getDecision()))) {
      response.setDecision(REJECT);
      response.setRejectionReason(SCORECALL2ERR);
    }
    return response;
  }

  @Override
  public ScoreCall2ErrContext getContext(ScoreResponseRiskJpaEntity response, Map<String, Long> score3RestrictionsMap) {
    return new ScoreCall2ErrContext(response.getDecision(), response.getScoreNodeId());
  }
}
