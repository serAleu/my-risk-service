package asia.atmonline.myriskservice.rules.basic.age2high;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.AGE2HIGH;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class BasicAge2HighRule extends BaseBasicRule<BasicAge2HighContext> {

  public BasicAge2HighRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<BasicSqsProducer> execute(BasicAge2HighContext context) {
    RiskResponseJpaEntity<BasicSqsProducer> response = super.execute(context);
    if (true) {
      response.setDecision(REJECT);
      response.setRejection_reason_code(AGE2HIGH);
    }
    return response;
  }

  @Override
  public BasicAge2HighContext getContext(Integer age, WorkingIndustry workingIndustry, OccupationType occupationType, Long income,
      AddressData registrationsAddressData) {
    return new BasicAge2HighContext(age);
  }
}
