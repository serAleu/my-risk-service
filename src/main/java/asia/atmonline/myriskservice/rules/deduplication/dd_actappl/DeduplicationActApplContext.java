package asia.atmonline.myriskservice.rules.deduplication.dd_actappl;

import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeduplicationActApplContext extends BaseDeduplicationContext {

  private final int countInProgress;

  public DeduplicationActApplContext(boolean isFinalChecks, int countInProgress) {
    super(isFinalChecks);
    this.countInProgress = countInProgress;
  }
}
