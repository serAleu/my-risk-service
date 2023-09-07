package asia.atmonline.myriskservice.rules.basic.industry;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicIndustryContext extends BaseBasicContext {

  private final WorkingIndustry workingIndustry;
  private final List<WorkingIndustryDictionary> dictionaryWorkingIndustries;

  public BasicIndustryContext(boolean isFinalChecks, WorkingIndustry workingIndustry, List<WorkingIndustryDictionary> dictionaryWorkingIndustries) {
    super(isFinalChecks);
    this.workingIndustry = workingIndustry;
    this.dictionaryWorkingIndustries = dictionaryWorkingIndustries;
  }
}
