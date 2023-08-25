package asia.atmonline.myriskservice.rules.score.call3;

import asia.atmonline.myriskservice.rules.score.BaseScoreContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreCall3Context extends BaseScoreContext {

  public ScoreCall3Context(Integer decision, Integer scoreNodeId) {
    super(decision, scoreNodeId);
  }
}
