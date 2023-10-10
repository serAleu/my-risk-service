package asia.atmonline.myriskservice.rules.basic.income2low;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicIncome2LowContext extends BaseBasicContext {

  private final Long income;
  private final Long permittedIncome;

  public BasicIncome2LowContext(RiskResponseJpaEntity response, boolean isFinalChecks, Long income, Long permittedIncome) {
    super(response, isFinalChecks);
    this.income = income;
    this.permittedIncome = permittedIncome;
  }
}
