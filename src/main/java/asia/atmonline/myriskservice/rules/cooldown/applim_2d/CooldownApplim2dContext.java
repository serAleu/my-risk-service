package asia.atmonline.myriskservice.rules.cooldown.applim_2d;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.cooldown.BaseCooldownContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CooldownApplim2dContext extends BaseCooldownContext {

  public static final Integer APPLICATIONS_LIMIT_NUM = 2;
  public static final Integer HOURS_TO_CHECK_NUM = 24;

  private Integer numOf2dApplications;

  public CooldownApplim2dContext(RiskResponseJpaEntity response) {
    super(response);
  }

  public CooldownApplim2dContext(RiskResponseJpaEntity response, Integer numOf2dApplications) {
    super(response);
    this.numOf2dApplications = numOf2dApplications;
  }
}
