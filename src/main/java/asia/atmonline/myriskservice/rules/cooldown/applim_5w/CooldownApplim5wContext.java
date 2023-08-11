package asia.atmonline.myriskservice.rules.cooldown.applim_5w;

import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooldownApplim5wContext extends BaseCooldownContext {

  public static final Integer APPLICATIONS_LIMIT_NUM = 5;
  public static final Integer DAYS_TO_CHECK_NUM = 7;

  private Integer numOf5wApplications;

  public CooldownApplim5wContext(Integer numOf5wApplications) {
    super();
    this.numOf5wApplications = numOf5wApplications;
  }
}
