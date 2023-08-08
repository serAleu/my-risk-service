package asia.atmonline.myriskservice.data.storage.entity.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RejectionReason {

  private String rejectionReasonCode;
  private String comment;

  public RejectionReason(String rejectionReasonCode) {
    this.rejectionReasonCode = rejectionReasonCode;
  }
}
