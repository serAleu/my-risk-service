package asia.atmonline.myriskservice.data.storage.entity.borrower;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CpaUtmData {

  @Column(name = "utm_click_hash")
  private String clickHash;
  @Column(name = "utm_affiliateId")
  private String affiliateId;

}
