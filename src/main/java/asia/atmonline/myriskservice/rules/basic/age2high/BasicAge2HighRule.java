package asia.atmonline.myriskservice.rules.basic.age2high;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.AGE2HIGH;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.AGE2HIGH_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BasicAge2HighRule extends BaseBasicRule<BasicAge2HighContext> {

  public BasicAge2HighRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseRiskJpaEntity execute(BasicAge2HighContext context) {
    RiskResponseRiskJpaEntity response = super.execute(context);
    if (context.getPermittedHighAge() < context.getAge()) {
      if (context.isFinalChecks) {
        response.setRejectionReason(AGE2HIGH_F);
      } else {
        response.setRejectionReason(AGE2HIGH);
      }
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public BasicAge2HighContext getContext(boolean isFinalChecks, List<AddressCityDictionary> dictionaryAddressCities, List<OccupationTypeDictionary> occupationTypeDictionaries,
      List<WorkingIndustryDictionary> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
      WorkingIndustryDictionary clientWorkingIndustry, OccupationTypeDictionary clientOccupationType, Long income, Long permittedIncome, AddressCityDictionary registrationsAddressData) {
    return new BasicAge2HighContext(isFinalChecks, age, permittedHighAge);
  }
}
