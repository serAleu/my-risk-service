package asia.atmonline.myriskservice.rules.deduplication.dd_actloan;

import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeduplicationActLoanContext extends BaseDeduplicationContext {

  private final int notFinishedCreditsCount;

  public DeduplicationActLoanContext(boolean isFinalChecks, int notFinishedCreditsCount) {
    super(isFinalChecks);
    this.notFinishedCreditsCount = notFinishedCreditsCount;
  }
}
