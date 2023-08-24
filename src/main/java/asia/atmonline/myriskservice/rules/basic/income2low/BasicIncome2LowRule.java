//package asia.atmonline.myriskservice.rules.basic.income2low;
//
//import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
//import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.INCOME2LOW;
//
//import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
//import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
//import asia.atmonline.myriskservice.data.storage.entity.property.DictionaryAddressCity;
//import asia.atmonline.myriskservice.data.storage.entity.property.DictionaryOccupationType;
//import asia.atmonline.myriskservice.data.storage.entity.property.DictionaryWorkingIndustry;
//import asia.atmonline.myriskservice.enums.borrower.OccupationType;
//import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
//import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
//import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
//import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
//import java.util.List;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BasicIncome2LowRule extends BaseBasicRule<BasicIncome2LowContext> {
//
//  public BasicIncome2LowRule(BlacklistChecksService blacklistChecksService) {
//    super(blacklistChecksService);
//  }
//
//  @Override
//  public RiskResponseJpaEntity<BasicSqsProducer> execute(BasicIncome2LowContext context) {
//    RiskResponseJpaEntity<BasicSqsProducer> response = super.execute(context);
//    if(context.getPermittedIncome() < context.getIncome()) {
//      response.setDecision(REJECT);
//      response.setRejection_reason_code(INCOME2LOW);
//    }
//    return response;
//  }
//
//  @Override
//  public BasicIncome2LowContext getContext(List<DictionaryAddressCity> dictionaryAddressCities, List<DictionaryOccupationType> dictionaryOccupationTypes,
//      List<DictionaryWorkingIndustry> dictionaryWorkingIndustries, Integer age, Integer permittedHighAge, Integer permittedLowAge,
//      WorkingIndustry workingIndustry, OccupationType occupationType, Long income, Long permittedIncome, AddressData registrationsAddressData) {
//    return new BasicIncome2LowContext(income, permittedIncome);
//  }
//}
