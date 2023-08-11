package asia.atmonline.myriskservice.rules.cooldown.active_app;

import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooldownActiveAppContext extends BaseCooldownContext {

  private List<CreditApplication> creditApplicationList;

  public CooldownActiveAppContext(List<CreditApplication> creditApplicationList) {
    super();
    this.creditApplicationList = creditApplicationList;
  }
}
