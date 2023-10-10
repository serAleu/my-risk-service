package asia.atmonline.myriskservice.rules.basic.age2low;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicAge2LowContext extends BaseBasicContext {

  private final Integer age;
  private final Integer permittedLowAge;

  public BasicAge2LowContext(RiskResponseJpaEntity response, boolean isFinalChecks, Integer age, Integer permittedLowAge) {
    super(response, isFinalChecks);
    this.age = age;
    this.permittedLowAge = permittedLowAge;
  }
}
