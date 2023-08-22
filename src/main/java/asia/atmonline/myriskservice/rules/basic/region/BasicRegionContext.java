package asia.atmonline.myriskservice.rules.basic.region;

import asia.atmonline.myriskservice.data.storage.entity.borrower.AddressData;
import asia.atmonline.myriskservice.rules.basic.BaseBasicContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasicRegionContext extends BaseBasicContext {

  private final AddressData registrationsAddressData;

  public BasicRegionContext(AddressData registrationsAddressData) {
    super();
    this.registrationsAddressData = registrationsAddressData;
  }
}
