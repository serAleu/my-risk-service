package asia.atmonline.myriskservice.rules.score.call2;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.score.BaseScoreContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreCall2Context extends BaseScoreContext {

  public ScoreCall2Context(RiskResponseJpaEntity response, Integer decision, Integer scoreNodeId) {
    super(response, decision, scoreNodeId);
  }
}
