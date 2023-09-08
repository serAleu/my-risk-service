package asia.atmonline.myriskservice.rules.basic.income2low;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.INCOME2LOW;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.INCOME2LOW_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BasicIncome2LowRule extends BaseBasicRule<BasicIncome2LowContext> {

  public BasicIncome2LowRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseRiskJpaEntity execute(BasicIncome2LowContext context) {
    RiskResponseRiskJpaEntity response = super.execute(context);
    if(context.getPermittedIncome() < context.getIncome()) {
      if (context.isFinalChecks) {
        response.setRejectionReason(INCOME2LOW_F);
      } else {
        response.setRejectionReason(INCOME2LOW);
      }
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public BasicIncome2LowContext getContext(boolean isFinalChecks, List<AddressCityDictionary> dictionaryAddressCities, List<OccupationTypeDictionary> occupationTypeDictionaries,
      List<WorkingIndustryDictionary> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
      WorkingIndustryDictionary clientWorkingIndustry, OccupationTypeDictionary clientOccupationType, Long income, Long permittedIncome, AddressCityDictionary registrationsAddressData) {
    return new BasicIncome2LowContext(isFinalChecks, income, permittedIncome);
  }
}
