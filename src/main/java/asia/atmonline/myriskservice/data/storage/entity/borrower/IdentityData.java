package asia.atmonline.myriskservice.data.storage.entity.borrower;

import jakarta.persistence.Column;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentityData {

  @Column(name = "id_primary_id")
  private String primaryId;
  @Column(name = "id_card_issuing_date")
  private LocalDate cardIssuingDate;
  @Column(name = "id_card_issuing_place")
  private String cardIssuingPlace;
}
