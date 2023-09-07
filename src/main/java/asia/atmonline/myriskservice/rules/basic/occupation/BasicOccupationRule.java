package asia.atmonline.myriskservice.rules.basic.occupation;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.OCCUPATION;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.OCCUPATION_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class BasicOccupationRule extends BaseBasicRule<BasicOccupationContext> {

  public BasicOccupationRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseRiskJpaEntity execute(BasicOccupationContext context) {
    RiskResponseRiskJpaEntity response = super.execute(context);
    context.getOccupationTypeDictionaries().forEach(dictionaryOccupationType -> {
      if(!dictionaryOccupationType.getActive() && dictionaryOccupationType.getNameEn().equalsIgnoreCase(context.getOccupationType().name())) {
        if (context.isFinalChecks) {
          response.setRejectionReason(OCCUPATION_F);
        } else {
          response.setRejectionReason(OCCUPATION);
        }
        response.setDecision(REJECT);
      }
    });
    return response;
  }

  @Override
  public BasicOccupationContext getContext(boolean isFinalChecks, List<AddressCityDictionary> dictionaryAddressCities, List<OccupationTypeDictionary> occupationTypeDictionaries,
      List<WorkingIndustryDictionary> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
      WorkingIndustry workingIndustry, OccupationType occupationType, Long income, Long permittedIncome, AddressCityDictionary registrationsAddressData) {
    return new BasicOccupationContext(isFinalChecks, occupationType, occupationTypeDictionaries);
  }
}
