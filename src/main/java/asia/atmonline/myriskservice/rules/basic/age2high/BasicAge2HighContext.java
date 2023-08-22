package asia.atmonline.myriskservice.rules.basic.age2high;

import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicAge2HighContext extends BaseBasicContext {

  private final Integer age;

  public BasicAge2HighContext(Integer age) {
    super();
    this.age = age;
  }
}
