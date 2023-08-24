//package asia.atmonline.myriskservice.rules.cooldown.active_loan;
//
//import static asia.atmonline.myriskservice.enums.risk.FinalDecision.REJECT;
//import static asia.atmonline.myriskservice.enums.risk.RejectionReasonCode.ACTIVE_LOAN;
//
//import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
//import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
//import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
//import asia.atmonline.myriskservice.producers.cooldown.CooldownSqsProducer;
//import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownRule;
//import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
//import java.util.List;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CooldownActiveLoanRule extends BaseCooldownRule<CooldownActiveLoanContext> {
//
//  public CooldownActiveLoanRule(BlacklistChecksService blacklistChecksService) {
//    super(blacklistChecksService);
//  }
//
//  @Override
//  public RiskResponseJpaEntity<CooldownSqsProducer> execute(CooldownActiveLoanContext context) {
//    RiskResponseJpaEntity<CooldownSqsProducer> response = super.execute(context);
//    context.getCreditList().forEach(credit -> {
//      if(credit.getStatus() != null && !credit.getStatus().isEnding()) {
//        response.setDecision(REJECT);
//        response.setRejection_reason_code(ACTIVE_LOAN);
//      }
//    });
//    return response;
//  }
//
//  @Override
//  public CooldownActiveLoanContext getContext(List<CreditApplication> creditApplicationList, List<Credit> creditList, Integer numOf2DApplications,
//      Integer numOf5wApplications, Integer numOf9mApplications) {
//    return new CooldownActiveLoanContext(creditList);
//  }
//}
