package asia.atmonline.myriskservice.rules.deduplication.dd_rejects;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeduplicationRejectsContext extends BaseDeduplicationContext {

  private final int approvedApplicationsCount;
  private final int rejectedApplicationsCount;

  public DeduplicationRejectsContext(RiskResponseJpaEntity response, boolean isFinalChecks, int approvedApplicationsCount, int rejectedApplicationsCount) {
    super(response, isFinalChecks);
    this.approvedApplicationsCount = approvedApplicationsCount;
    this.rejectedApplicationsCount = rejectedApplicationsCount;
  }
}
