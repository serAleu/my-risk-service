package asia.atmonline.myriskservice.rules.score;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseScoreContext extends BaseRuleContext {

  public static final List<Integer> DECISION_POSSIBLE_VALUES_LIST = List.of(0, 1);

  private final Integer decision;
  private final Integer scoreNodeId;

  public BaseScoreContext(Integer decision, Integer scoreNodeId) {
    super(new RiskResponseJpaEntity());
    this.decision = decision;
    this.scoreNodeId = scoreNodeId;
  }
}
