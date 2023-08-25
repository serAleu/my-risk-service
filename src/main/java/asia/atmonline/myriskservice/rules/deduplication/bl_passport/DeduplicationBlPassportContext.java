package asia.atmonline.myriskservice.rules.deduplication.bl_passport;

import asia.atmonline.myriskservice.rules.deduplication.BaseDeduplicationContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeduplicationBlPassportContext extends BaseDeduplicationContext {

  private boolean isPassportNumMatchedWithBlPassportNum;

  public DeduplicationBlPassportContext(boolean isFinalChecks, boolean isPassportNumMatchedWithBlPassportNum) {
    super(isFinalChecks);
    this.isPassportNumMatchedWithBlPassportNum = isPassportNumMatchedWithBlPassportNum;
  }
}
