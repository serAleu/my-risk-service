package asia.atmonline.myriskservice.rules.basic.region;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.REGION;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
import asia.atmonline.myriskservice.data.storage.entity.property.DictionaryAddressCity;
import asia.atmonline.myriskservice.data.storage.entity.property.DictionaryOccupationType;
import asia.atmonline.myriskservice.data.storage.entity.property.DictionaryWorkingIndustry;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
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
  public RiskResponseJpaEntity<BasicSqsProducer> execute(BasicRegionContext context) {
    RiskResponseJpaEntity<BasicSqsProducer> response = super.execute(context);
    context.getDictionaryAddressCities().forEach(dictionaryAddressCity -> {
      if (!dictionaryAddressCity.getProhibited() && (
          Objects.equals(context.getRegistrationsAddressData().getAddressCityId(), dictionaryAddressCity.getStateId())
              || context.getRegistrationsAddressData().getCity().equalsIgnoreCase(dictionaryAddressCity.getNameEn())
              || context.getRegistrationsAddressData().getCity().equalsIgnoreCase(dictionaryAddressCity.getNameMy()))) {
        response.setDecision(REJECT);
        response.setRejection_reason_code(REGION);
      }
    });
    return response;
  }

  @Override
  public BasicRegionContext getContext(Boolean isFinalChecks, List<DictionaryAddressCity> dictionaryAddressCities, List<DictionaryOccupationType> dictionaryOccupationTypes,
      List<DictionaryWorkingIndustry> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
      WorkingIndustry workingIndustry, OccupationType occupationType, Long income, Long permittedIncome, AddressData registrationsAddressData) {
    return new BasicRegionContext(isFinalChecks, registrationsAddressData, dictionaryAddressCities);
  }
}
