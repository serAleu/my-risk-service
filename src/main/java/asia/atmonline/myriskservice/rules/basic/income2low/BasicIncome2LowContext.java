package asia.atmonline.myriskservice.rules.basic.income2low;

import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicIncome2LowContext extends BaseBasicContext {

  private final Long income;

  public BasicIncome2LowContext(Long income) {
    super();
    this.income = income;
  }
}
