package asia.atmonline.myriskservice.rules.deduplication.dd_actloan;

import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationActLoanRule extends BaseDeduplicationRule<DeduplicationActLoanContext> {

  public DeduplicationActLoanRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<DeduplicationSqsProducer> execute(DeduplicationActLoanContext context) {
    RiskResponseJpaEntity<DeduplicationSqsProducer> response = super.execute(context);
    return response;
  }

  @Override
  public DeduplicationActLoanContext getContext(boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationActLoanContext();
  }
}
