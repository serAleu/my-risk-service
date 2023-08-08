package asia.atmonline.myriskservice.data.storage.entity.credit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Embeddable
@Accessors(chain = true)
public class EarlyRepaymentSnapshotData implements Serializable {

  @Column(name = "early_repayment_fee", precision = 19, scale = 2, nullable = false)
  private BigDecimal fee;

  @Column(name = "paid_early_repayment_fee", precision = 19, scale = 2, nullable = false)
  private BigDecimal paidFee;

  @Column(name = "early_repayment_close_amount", precision = 19, scale = 2, nullable = false)
  private BigDecimal closeAmount;

  @Column(name = "early_repayment_saving_amount", precision = 19, scale = 2, nullable = false)
  private BigDecimal savingAmount;

}
