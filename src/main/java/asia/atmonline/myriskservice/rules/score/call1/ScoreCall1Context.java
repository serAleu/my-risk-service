package asia.atmonline.myriskservice.rules.score.call1;

import asia.atmonline.myriskservice.rules.score.BaseScoreContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreCall1Context extends BaseScoreContext {

  public ScoreCall1Context(Integer decision, Integer scoreNodeId) {
    super(decision, scoreNodeId);
  }
}
