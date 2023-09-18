package asia.atmonline.myriskservice.data.storage.entity.borrower;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;

@Embeddable
@Data
public class CpaUtmData implements Serializable {

  @Column(name = "utm_click_hash")
  private String clickHash;
  @Column(name = "utm_affiliateId")
  private String affiliateId;

}
