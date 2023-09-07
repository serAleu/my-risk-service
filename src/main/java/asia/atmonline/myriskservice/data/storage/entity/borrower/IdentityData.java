package asia.atmonline.myriskservice.data.storage.entity.borrower;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class IdentityData {

  @Column(name = "id_primary_id")
  private String primaryId;
}
