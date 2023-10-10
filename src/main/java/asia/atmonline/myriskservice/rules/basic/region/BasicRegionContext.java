package asia.atmonline.myriskservice.rules.basic.region;

import asia.atmonline.myriskservice.data.risk.entity.RiskResponseJpaEntity;
import asia.atmonline.myriskservice.data.storage.entity.dictionary.impl.AddressCityDictionary;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicRegionContext extends BaseBasicContext {

  private final AddressCityDictionary clientAddressCity;
  private final List<AddressCityDictionary> dictionaryAddressCities;

  public BasicRegionContext(RiskResponseJpaEntity response, boolean isFinalChecks, AddressCityDictionary clientAddressCity, List<AddressCityDictionary> dictionaryAddressCities) {
    super(response, isFinalChecks);
    this.clientAddressCity = clientAddressCity;
    this.dictionaryAddressCities = dictionaryAddressCities;
  }
}
