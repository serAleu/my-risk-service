package asia.atmonline.myriskservice.rules.basic.age2low;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.AGE2LOW;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.AGE2LOW_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BasicAge2LowRule extends BaseBasicRule<BasicAge2LowContext> {

  public BasicAge2LowRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(BasicAge2LowContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if (context.getPermittedLowAge() > context.getAge()) {
      if (context.isFinalChecks) {
        response.setRejectionReason(AGE2LOW_F);
      } else {
        response.setRejectionReason(AGE2LOW);
      }
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public BasicAge2LowContext getContext(RiskResponseJpaEntity response, boolean isFinalChecks, List<AddressCityDictionary> dictionaryAddressCities, List<OccupationTypeDictionary> occupationTypeDictionaries,
      List<WorkingIndustryDictionary> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
      WorkingIndustryDictionary clientWorkingIndustry, OccupationTypeDictionary clientOccupationType, Long income, Long permittedIncome, AddressCityDictionary registrationsAddressData) {
    return new BasicAge2LowContext(response, isFinalChecks, age, permittedLowAge);
  }
}
