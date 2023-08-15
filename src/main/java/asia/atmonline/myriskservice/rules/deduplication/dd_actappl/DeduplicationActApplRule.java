package asia.atmonline.myriskservice.rules.deduplication.dd_actappl;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationActApplRule extends BaseDeduplicationRule<DeduplicationActApplContext> {

  public DeduplicationActApplRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<DeduplicationSqsProducer> execute(DeduplicationActApplContext context) {
    RiskResponseJpaEntity<DeduplicationSqsProducer> response = super.execute(context);
    return response;
  }

  @Override
  public DeduplicationActApplContext getContext(boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationActApplContext();
  }
}
