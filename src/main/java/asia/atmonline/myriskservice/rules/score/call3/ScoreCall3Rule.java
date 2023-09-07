package asia.atmonline.myriskservice.rules.score.call3;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.SCORECALL3;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.external_responses.ScoreResponseRiskJpaEntity;
import asia.atmonline.myriskservice.rules.score.BaseScoreRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ScoreCall3Rule extends BaseScoreRule<ScoreCall3Context> {

  public ScoreCall3Rule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseRiskJpaEntity execute(ScoreCall3Context context) {
    RiskResponseRiskJpaEntity response = super.execute(context);
    if(context.getScoreNodeId() == 3 && 0 == context.getDecision()) {
      response.setDecision(REJECT);
      response.setRejectionReason(SCORECALL3);
    }
    return response;
  }

  @Override
  public ScoreCall3Context getContext(ScoreResponseRiskJpaEntity response, Map<String, Long> score3RestrictionsMap) {
    return new ScoreCall3Context(response.getDecision(), response.getScoreNodeId());
  }
}
