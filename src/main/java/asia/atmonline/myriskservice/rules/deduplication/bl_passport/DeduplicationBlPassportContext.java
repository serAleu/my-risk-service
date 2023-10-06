package asia.atmonline.myriskservice.rules.deduplication.bl_passport;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeduplicationBlPassportContext extends BaseDeduplicationContext {

  private boolean isPassportNumMatchedWithBlPassportNum;

  public DeduplicationBlPassportContext(RiskResponseJpaEntity response, boolean isFinalChecks, boolean isPassportNumMatchedWithBlPassportNum) {
    super(response, isFinalChecks);
    this.isPassportNumMatchedWithBlPassportNum = isPassportNumMatchedWithBlPassportNum;
  }
}
