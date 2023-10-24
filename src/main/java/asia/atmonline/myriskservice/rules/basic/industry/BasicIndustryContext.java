package asia.atmonline.myriskservice.rules.basic.industry;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicIndustryContext extends BaseBasicContext {

  private final WorkingIndustryDictionary clientWorkingIndustry;

  public BasicIndustryContext(RiskResponseJpaEntity response, boolean isFinalChecks, WorkingIndustryDictionary clientWorkingIndustry) {
    super(response, isFinalChecks);
    this.clientWorkingIndustry = clientWorkingIndustry;
  }
}
