package asia.atmonline.myriskservice.rules.basic.industry;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.INDUSTRY;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.INDUSTRY_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BasicIndustryRule extends BaseBasicRule<BasicIndustryContext> {

  public BasicIndustryRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseRiskJpaEntity execute(BasicIndustryContext context) {
    RiskResponseRiskJpaEntity response = super.execute(context);
    context.getDictionaryWorkingIndustries().forEach(dictionaryWorkingIndustry -> {
      if (!dictionaryWorkingIndustry.getActive()
          && (dictionaryWorkingIndustry.getNameEn().equalsIgnoreCase(context.getClientWorkingIndustry().getNameEn())
          || dictionaryWorkingIndustry.getNameMy().equalsIgnoreCase(context.getClientWorkingIndustry().getNameMy()))) {
        if (context.isFinalChecks) {
          response.setRejectionReason(INDUSTRY_F);
        } else {
          response.setRejectionReason(INDUSTRY);
        }
        response.setDecision(REJECT);
      }
    });
    return response;
  }

  @Override
  public BasicIndustryContext getContext(boolean isFinalChecks, List<AddressCityDictionary> dictionaryAddressCities,
      List<OccupationTypeDictionary> occupationTypeDictionaries,
      List<WorkingIndustryDictionary> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
      WorkingIndustryDictionary clientWorkingIndustry, OccupationTypeDictionary clientOccupationType, Long income, Long permittedIncome,
      AddressCityDictionary registrationsAddressData) {
    return new BasicIndustryContext(isFinalChecks, clientWorkingIndustry, dictionaryWorkingIndustries);
  }
}
