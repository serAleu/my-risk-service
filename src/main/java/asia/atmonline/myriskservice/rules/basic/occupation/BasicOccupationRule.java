package asia.atmonline.myriskservice.rules.basic.occupation;

import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.OCCUPATION;
import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.OCCUPATION_F;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.OccupationTypeDictionary;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.WorkingIndustryDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class BasicOccupationRule extends BaseBasicRule<BasicOccupationContext> {

  public BasicOccupationRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity execute(BasicOccupationContext context) {
    RiskResponseJpaEntity response = super.execute(context);
    if (context.getClientOccupationType().isProhibited()) {
      response.setRejectionReason(context.isFinalChecks ? OCCUPATION_F : OCCUPATION);
      response.setDecision(REJECT);
    }
    return response;
  }

  @Override
  public BasicOccupationContext getContext(RiskResponseJpaEntity response, boolean isFinalChecks, Integer age, Integer permittedHighAge,
      Integer permittedLowAge,
      WorkingIndustryDictionary clientWorkingIndustry, OccupationTypeDictionary clientOccupationType, Long income, Long permittedIncome,
      AddressCityDictionary registrationsAddressData) {
    return new BasicOccupationContext(response, isFinalChecks, clientOccupationType);
  }
}
