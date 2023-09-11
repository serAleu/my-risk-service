package asia.atmonline.myriskservice.rules.cooldown.active_app;

import asia.atmonline.myriskservice.enums.application.CreditApplicationStatus;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooldownActiveAppContext extends BaseCooldownContext {

  private List<CreditApplicationStatus> creditApplicationStatuses;

  public CooldownActiveAppContext(List<CreditApplicationStatus> creditApplicationStatuses) {
    super();
    this.creditApplicationStatuses = creditApplicationStatuses;
  }
}
