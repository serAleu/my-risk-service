package asia.atmonline.myriskservice.rules.basic.region;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.REGION;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class BasicRegionRule extends BaseBasicRule<BasicRegionContext> {

  public BasicRegionRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<BasicSqsProducer> execute(BasicRegionContext context) {
    RiskResponseJpaEntity<BasicSqsProducer> response = super.execute(context);
    if(true) {
      response.setDecision(REJECT);
      response.setRejectionReasonCode(REGION);
    }
    return response;
  }

  @Override
  public BasicRegionContext getContext(Integer age, WorkingIndustry workingIndustry, OccupationType occupationType, Long income,
      AddressData registrationsAddressData) {
    return new BasicRegionContext(registrationsAddressData);
  }
}
