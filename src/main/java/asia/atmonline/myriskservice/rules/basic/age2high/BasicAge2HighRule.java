package asia.atmonline.myriskservice.rules.basic.age2high;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.AGE2HIGH;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.AGE2HIGH_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
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
  public BasicAge2HighContext getContext(RiskResponseJpaEntity response, boolean isFinalChecks, Integer age, Integer permittedHighAge,
      Integer permittedLowAge,
      WorkingIndustryDictionary clientWorkingIndustry, OccupationTypeDictionary clientOccupationType, Long income, Long permittedIncome,
      AddressCityDictionary registrationsAddressData) {
    return new BasicAge2HighContext(response, isFinalChecks, age, permittedHighAge);
  }
}
