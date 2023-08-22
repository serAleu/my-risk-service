package asia.atmonline.myriskservice.rules.basic.age2low;

import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicAge2LowContext extends BaseBasicContext {

  private final Integer age;

  public BasicAge2LowContext(Integer age) {
    super();
    this.age = age;
  }
}
