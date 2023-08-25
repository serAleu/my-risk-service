package asia.atmonline.myriskservice.rules.basic.age2high;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.AGE2HIGH;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.AGE2HIGH_F;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
import asia.atmonline.myriskservice.data.storage.entity.property.DictionaryAddressCity;
import asia.atmonline.myriskservice.data.storage.entity.property.DictionaryOccupationType;
import asia.atmonline.myriskservice.data.storage.entity.property.DictionaryWorkingIndustry;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
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
  public RiskResponseJpaEntity execute(BasicAge2HighContext context) {
    RiskResponseJpaEntity response = super.execute(context);
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
  public BasicAge2HighContext getContext(boolean isFinalChecks, List<DictionaryAddressCity> dictionaryAddressCities, List<DictionaryOccupationType> dictionaryOccupationTypes,
      List<DictionaryWorkingIndustry> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
      WorkingIndustry workingIndustry, OccupationType occupationType, Long income, Long permittedIncome, AddressData registrationsAddressData) {
    return new BasicAge2HighContext(isFinalChecks, age, permittedHighAge);
  }
}
