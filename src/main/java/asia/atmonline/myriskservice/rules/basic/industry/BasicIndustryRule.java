package asia.atmonline.myriskservice.rules.basic.industry;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.INDUSTRY;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.INDUSTRY_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class BasicIndustryRule extends BaseBasicRule<BasicIndustryContext> {

  public BasicIndustryRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(BasicIndustryContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if (!context.getClientWorkingIndustry().getActive()) {
      response.setRejectionReason(context.isFinalChecks ? INDUSTRY_F : INDUSTRY);
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public BasicIndustryContext getContext(RiskResponseJpaEntity response, boolean isFinalChecks, Integer age, Integer permittedHighAge,
      Integer permittedLowAge,
      WorkingIndustryDictionary clientWorkingIndustry, OccupationTypeDictionary clientOccupationType, Long income, Long permittedIncome,
      AddressCityDictionary registrationsAddressData) {
    return new BasicIndustryContext(response, isFinalChecks, clientWorkingIndustry);
  }
}
