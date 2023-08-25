package asia.atmonline.myriskservice.rules.basic.occupation;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.OCCUPATION;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.OCCUPATION_F;

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
public class BasicOccupationRule extends BaseBasicRule<BasicOccupationContext> {

  public BasicOccupationRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(BasicOccupationContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    context.getDictionaryOccupationTypes().forEach(dictionaryOccupationType -> {
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
  public BasicOccupationContext getContext(boolean isFinalChecks, List<DictionaryAddressCity> dictionaryAddressCities, List<DictionaryOccupationType> dictionaryOccupationTypes,
      List<DictionaryWorkingIndustry> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
      WorkingIndustry workingIndustry, OccupationType occupationType, Long income, Long permittedIncome, AddressData registrationsAddressData) {
    return new BasicOccupationContext(isFinalChecks, occupationType, dictionaryOccupationTypes);
  }
}
