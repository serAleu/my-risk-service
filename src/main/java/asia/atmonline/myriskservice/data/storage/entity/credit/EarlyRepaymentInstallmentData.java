package asia.atmonline.myriskservice.data.storage.entity.credit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.Data;

@Data
@Embeddable
public class EarlyRepaymentInstallmentData {

  @Column(name = "early_repayment_fee", precision = 19, scale = 2, nullable = false)
  private BigDecimal fee;

  @Column(name = "early_repayment_close_amount", precision = 19, scale = 2, nullable = false)
  private BigDecimal closeAmount;

  @Column(name = "early_repayment_saving_amount", precision = 19, scale = 2, nullable = false)
  private BigDecimal savingAmount;
}
