package asia.atmonline.myriskservice.rules.basic.region;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.REGION;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.REGION_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
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
  public RiskResponseJpaEntity execute(BasicRegionContext context) {
    RiskResponseJpaEntity response = super.execute(context);
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
      WorkingIndustryDictionary clientWorkingIndustry, OccupationTypeDictionary clientOccupationType, Long income, Long permittedIncome, AddressCityDictionary registrationsAddressData) {
    return new BasicRegionContext(isFinalChecks, registrationsAddressData, dictionaryAddressCities);
  }
}
