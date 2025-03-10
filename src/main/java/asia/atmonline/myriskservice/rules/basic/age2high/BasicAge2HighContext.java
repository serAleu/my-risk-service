package asia.atmonline.myriskservice.rules.basic.age2high;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicAge2HighContext extends BaseBasicContext {

  private final Integer age;
  private final Integer permittedHighAge;

  public BasicAge2HighContext(RiskResponseJpaEntity response, boolean isFinalChecks, Integer age, Integer permittedHighAge) {
    super(response, isFinalChecks);
    this.age = age;
    this.permittedHighAge = permittedHighAge;
  }
}
