package asia.atmonline.myriskservice.rules;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseRuleContext {

  private RiskResponseJpaEntity riskResponseJpaEntity;

}
