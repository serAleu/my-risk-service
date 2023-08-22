package asia.atmonline.myriskservice.rules.basic.industry;

import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicIndustryContext extends BaseBasicContext {

  private final WorkingIndustry workingIndustry;

  public BasicIndustryContext(WorkingIndustry workingIndustry) {
    super();
    this.workingIndustry = workingIndustry;
  }
}
