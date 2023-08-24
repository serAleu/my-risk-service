package asia.atmonline.myriskservice.rules.basic.region;

import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
import asia.atmonline.myriskservice.data.storage.entity.property.DictionaryAddressCity;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicRegionContext extends BaseBasicContext {

  private final AddressData registrationsAddressData;
  private final List<DictionaryAddressCity> dictionaryAddressCities;

  public BasicRegionContext(Boolean isFinalChecks, AddressData registrationsAddressData, List<DictionaryAddressCity> dictionaryAddressCities) {
    super(isFinalChecks);
    this.registrationsAddressData = registrationsAddressData;
    this.dictionaryAddressCities = dictionaryAddressCities;
  }
}
