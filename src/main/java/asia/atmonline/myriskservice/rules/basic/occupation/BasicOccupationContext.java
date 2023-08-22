package asia.atmonline.myriskservice.rules.basic.occupation;

import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicOccupationContext extends BaseBasicContext {

  private final OccupationType occupationType;

  public BasicOccupationContext(OccupationType occupationType) {
    super();
    this.occupationType = occupationType;
  }
}
