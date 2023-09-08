package asia.atmonline.myriskservice.rules.basic.industry;

import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicIndustryContext extends BaseBasicContext {

  private final WorkingIndustryDictionary clientWorkingIndustry;
  private final List<WorkingIndustryDictionary> dictionaryWorkingIndustries;

  public BasicIndustryContext(boolean isFinalChecks, WorkingIndustryDictionary clientWorkingIndustry, List<WorkingIndustryDictionary> dictionaryWorkingIndustries) {
    super(isFinalChecks);
    this.clientWorkingIndustry = clientWorkingIndustry;
    this.dictionaryWorkingIndustries = dictionaryWorkingIndustries;
  }
}
