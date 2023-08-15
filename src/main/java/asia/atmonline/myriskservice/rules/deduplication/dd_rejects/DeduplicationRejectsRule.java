package asia.atmonline.myriskservice.rules.deduplication.dd_rejects;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationRejectsRule extends BaseDeduplicationRule<DeduplicationRejectsContext> {

  public DeduplicationRejectsRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<DeduplicationSqsProducer> execute(DeduplicationRejectsContext context) {
    RiskResponseJpaEntity<DeduplicationSqsProducer> response = super.execute(context);
//    if() {
//
//    }
    return response;
  }

  @Override
  public DeduplicationRejectsContext getContext(boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationRejectsContext();
  }
}
