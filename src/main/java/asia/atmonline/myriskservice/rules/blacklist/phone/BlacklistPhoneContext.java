package asia.atmonline.myriskservice.rules.blacklist.phone;

import asia.atmonline.myriskservice.data.entity.blacklists.entity.impl.BlacklistPhoneJpaEntity;
import asia.atmonline.myriskservice.data.entity.risk.responses.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.rules.BaseRuleContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlacklistPhoneContext extends BaseRuleContext {

  private List<BlacklistPhoneJpaEntity> entities;
  private Integer numberOfNotFinishedCredits;
  private Integer numberOfFinishedCredits;

  public BlacklistPhoneContext(List<BlacklistPhoneJpaEntity> entities, Integer numberOfNotFinishedCredits, Integer numberOfFinishedCredits) {
    super(new RiskResponseJpaEntity());
    this.entities = entities;
    this.numberOfNotFinishedCredits = numberOfNotFinishedCredits;
    this.numberOfFinishedCredits = numberOfFinishedCredits;
  }
}
