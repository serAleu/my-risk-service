package asia.atmonline.myriskservice.rules.deduplication.dd_maxdpd;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.deduplication.DeduplicationSqsProducer;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationRule;
import asia.atmonline.myriskservice.services.blacklists.BlacklistChecksService;
import org.springframework.stereotype.Component;

@Component
public class DeduplicationMaxDpdRule extends BaseDeduplicationRule<DeduplicationMaxDpdContext> {

  public DeduplicationMaxDpdRule(BlacklistChecksService blacklistChecksService) {
    super(blacklistChecksService);
  }

  @Override
  public RiskResponseJpaEntity<DeduplicationSqsProducer> execute(DeduplicationMaxDpdContext context) {
    RiskResponseJpaEntity<DeduplicationSqsProducer> response = super.execute(context);
    return response;
  }

  @Override
  public DeduplicationMaxDpdContext getContext(boolean isBankAccountMatchedWithBlAccount, boolean isPassportNumMatchedWithBlPassportNum) {
    return new DeduplicationMaxDpdContext();
  }
}
