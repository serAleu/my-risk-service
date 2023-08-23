package asia.atmonline.myriskservice.rules.basic.industry;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.INDUSTRY;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class BasicIndustryRule extends BaseBasicRule<BasicIndustryContext> {

  public BasicIndustryRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<BasicSqsProducer> execute(BasicIndustryContext context) {
    RiskResponseJpaEntity<BasicSqsProducer> response = super.execute(context);
    if(true) {
      response.setDecision(REJECT);
      response.setRejection_reason_code(INDUSTRY);
    }
    return response;
  }

  @Override
  public BasicIndustryContext getContext(Integer age, WorkingIndustry workingIndustry, OccupationType occupationType, Long income,
      AddressData registrationsAddressData) {
    return new BasicIndustryContext(workingIndustry);
  }
}
