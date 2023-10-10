package asia.atmonline.myriskservice.rules.deduplication.dd_actappl;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeduplicationActApplContext extends BaseDeduplicationContext {

  private final int countInProgress;

  public DeduplicationActApplContext(RiskResponseJpaEntity response, boolean isFinalChecks, int countInProgress) {
    super(response, isFinalChecks);
    this.countInProgress = countInProgress;
  }
}
