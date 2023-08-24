package asia.atmonline.myriskservice.rules.basic.industry;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.INDUSTRY;

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
import org.springframework.stereotype.Component;

@Component
public class BasicIndustryRule extends BaseBasicRule<BasicIndustryContext> {

  public BasicIndustryRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<BasicSqsProducer> execute(BasicIndustryContext context) {
    RiskResponseJpaEntity<BasicSqsProducer> response = super.execute(context);
    context.getDictionaryWorkingIndustries().forEach(dictionaryWorkingIndustry -> {
      if(!dictionaryWorkingIndustry.getActive() && dictionaryWorkingIndustry.getNameEn().equalsIgnoreCase(context.getWorkingIndustry().name())) {
        response.setDecision(REJECT);
        response.setRejection_reason_code(INDUSTRY);
      }
    });
    return response;
  }

  @Override
  public BasicIndustryContext getContext(Boolean isFinalChecks, List<DictionaryAddressCity> dictionaryAddressCities, List<DictionaryOccupationType> dictionaryOccupationTypes,
      List<DictionaryWorkingIndustry> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
      WorkingIndustry workingIndustry, OccupationType occupationType, Long income, Long permittedIncome, AddressData registrationsAddressData) {
    return new BasicIndustryContext(isFinalChecks, workingIndustry, dictionaryWorkingIndustries);
  }
}
