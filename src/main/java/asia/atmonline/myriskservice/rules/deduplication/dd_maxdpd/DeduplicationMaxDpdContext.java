package asia.atmonline.myriskservice.rules.deduplication.dd_maxdpd;

import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeduplicationMaxDpdContext extends BaseDeduplicationContext {

  public static final Integer MAX_HISTORICAL_OVERDUE_DAYS = 30;
  private final int maxDpdCount;

  public DeduplicationMaxDpdContext(boolean isFinalChecks, int maxDpdCount) {
    super(isFinalChecks);
    this.maxDpdCount = maxDpdCount;
  }
}
