package asia.atmonline.myriskservice.data.storage.entity.borrower;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "borrower_additional_id_number_data", schema = "my-back")
@NoArgsConstructor
@Getter
@Setter
public class BorrowerAdditionalIdNumber extends BaseStorageEntity {

  @Column(name = "borrower_id", nullable = false, updatable = false)
  private Long borrowerId;

  @Column(name = "value", nullable = false)
  private String value;
}
