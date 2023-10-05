package asia.atmonline.myriskservice.rules.score.call2err;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.score.BaseScoreContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreCall2ErrContext extends BaseScoreContext {

  public ScoreCall2ErrContext(RiskResponseJpaEntity response, Integer decision, Integer scoreNodeId) {
    super(response, decision, scoreNodeId);
  }
}
