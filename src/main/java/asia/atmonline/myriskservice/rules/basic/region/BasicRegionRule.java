package asia.atmonline.myriskservice.rules.basic.region;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.REGION;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.REGION_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseRiskJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class BasicRegionRule extends BaseBasicRule<BasicRegionContext> {

  public BasicRegionRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseRiskJpaEntity execute(BasicRegionContext context) {
    RiskResponseRiskJpaEntity response = super.execute(context);
    context.getDictionaryAddressCities().forEach(dictionaryAddressCity -> {
      if (!dictionaryAddressCity.isProhibited() && (
          Objects.equals(context.getClientAddressCity().getState().getId(), dictionaryAddressCity.getState().getId())
              || context.getClientAddressCity().getNameEn().equalsIgnoreCase(dictionaryAddressCity.getNameEn())
              || context.getClientAddressCity().getNameMy().equalsIgnoreCase(dictionaryAddressCity.getNameMy()))) {
        if (context.isFinalChecks) {
          response.setRejectionReason(REGION_F);
        } else {
          response.setRejectionReason(REGION);
        }
        response.setDecision(REJECT);
      }
    });
    return response;
  }

  @Override
  public BasicRegionContext getContext(boolean isFinalChecks, List<AddressCityDictionary> dictionaryAddressCities, List<OccupationTypeDictionary> occupationTypeDictionaries,
      List<WorkingIndustryDictionary> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
      WorkingIndustry workingIndustry, OccupationType occupationType, Long income, Long permittedIncome, AddressCityDictionary registrationsAddressData) {
    return new BasicRegionContext(isFinalChecks, registrationsAddressData, dictionaryAddressCities);
  }
}
