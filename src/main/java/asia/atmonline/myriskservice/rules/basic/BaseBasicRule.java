package asia.atmonline.myriskservice.rules.basic;

import static asia.atmonline.myriskservice.enums.risk.CheckType.BASIC;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
import asia.atmonline.myriskservice.enums.borrower.OccupationType;
import asia.atmonline.myriskservice.enums.borrower.WorkingIndustry;
import asia.atmonline.myriskservice.producers.basic.BasicSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;

public abstract class BaseBasicRule<P extends BaseBasicContext> extends BaseRule<P> {

  public BaseBasicRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public RiskResponseJpaEntity<BasicSqsProducer> execute(P context) {
    return (RiskResponseJpaEntity<BasicSqsProducer>) getApprovedResponse(context.getRiskResponseJpaEntity().getCreditApplicationId(), BASIC,
        context.getRiskResponseJpaEntity());
  }

  public abstract P getContext(Integer age, WorkingIndustry workingIndustry, OccupationType occupationType, Long income,
      AddressData registrationsAddressData);
}

