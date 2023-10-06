package asia.atmonline.myriskservice.rules.deduplication.bl_account;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeduplicationBlAccountContext extends BaseDeduplicationContext {

  private boolean isBankAccountMatchedWithBlAccount;

  public DeduplicationBlAccountContext(RiskResponseJpaEntity response, boolean isFinalChecks, boolean isBankAccountMatchedWithBlAccount) {
    super(response, isFinalChecks);
    this.isBankAccountMatchedWithBlAccount = isBankAccountMatchedWithBlAccount;
  }
}
