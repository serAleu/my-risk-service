package asia.atmonline.myriskservice.rules.blacklist;

import asia.atmonline.myriskservice.messages.response.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.producers.blacklist.BlacklistSqsProducer;
import asia.atmonline.myriskservice.rules.BaseRuleContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlacklistPhoneContext extends BaseRuleContext {

  private String phoneNum;

  public BlacklistPhoneContext(String phoneNum) {
    super(new RiskResponseJpaEntity<BlacklistSqsProducer>());
    this.phoneNum = phoneNum;
  }
}
