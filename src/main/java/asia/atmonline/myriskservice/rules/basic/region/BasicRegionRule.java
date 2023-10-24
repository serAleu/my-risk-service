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
import org.springframework.stereotype.Component;

@Component
public class BasicRegionRule extends BaseBasicRule<BasicRegionContext> {

  public BasicRegionRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(BasicRegionContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if (context.getClientAddressCity().isProhibited()) {
      response.setRejectionReason(context.isFinalChecks ? REGION_F : REGION);
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public BasicRegionContext getContext(RiskResponseJpaEntity response, boolean isFinalChecks, Integer age, Integer permittedHighAge,
      Integer permittedLowAge,
      WorkingIndustryDictionary clientWorkingIndustry, OccupationTypeDictionary clientOccupationType, Long income, Long permittedIncome,
      AddressCityDictionary residencyCity) {
    return new BasicRegionContext(response, isFinalChecks, residencyCity);
  }
}
