package asia.atmonline.myriskservice.rules.cooldown.applim_9m;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooldownApplim9mContext extends BaseCooldownContext {

  public static final Integer APPLICATIONS_LIMIT_NUM = 9;
  public static final Integer DAYS_TO_CHECK_NUM = 30;

  private Integer numOf9mApplications;

  public CooldownApplim9mContext(RiskResponseJpaEntity response, Integer numOf9mApplications) {
    super(response);
    this.numOf9mApplications = numOf9mApplications;
  }
}
