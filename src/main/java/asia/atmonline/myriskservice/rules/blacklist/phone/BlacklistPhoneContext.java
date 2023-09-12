package asia.atmonline.myriskservice.rules.blacklist.phone;

import asia.atmonline.myriskservice.data.risk.entity.blacklists.impl.BlacklistPhoneRiskJpaEntity;
import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlacklistPhoneContext extends BaseRuleContext {

  private List<BlacklistPhoneRiskJpaEntity> entities;
  private Integer numberOfNotFinishedCredits;
  private Integer numberOfFinishedCredits;

  public BlacklistPhoneContext(List<BlacklistPhoneRiskJpaEntity> entities, Integer numberOfNotFinishedCredits, Integer numberOfFinishedCredits) {
    super(new RiskResponseJpaEntity());
    this.entities = entities;
    this.numberOfNotFinishedCredits = numberOfNotFinishedCredits;
    this.numberOfFinishedCredits = numberOfFinishedCredits;
  }
}
