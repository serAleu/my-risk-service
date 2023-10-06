package asia.atmonline.myriskservice.rules.cooldown.active_loan;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.credit.Credit;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooldownActiveLoanContext extends BaseCooldownContext {

  private List<Credit> creditList;

  public CooldownActiveLoanContext(RiskResponseJpaEntity response, List<Credit> creditList) {
    super(response);
    this.creditList = creditList;
  }
}
