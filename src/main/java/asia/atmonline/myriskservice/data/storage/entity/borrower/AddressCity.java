package asia.atmonline.myriskservice.data.storage.entity.borrower;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address_city")
@NoArgsConstructor
@Getter
@Setter
public class AddressCity extends BaseStorageEntity {

//  @Enumerated(EnumType.STRING)
//  @Column(name = "district", nullable = false)
//  private District district;

  @Column(name = "nameEn", nullable = false)
  private String nameEn;

  @Column(name = "nameSi", nullable = false)
  private String nameSi;

  @Column(name = "nameta", nullable = false)
  private String nameTa;

  @Column(name = "prohibited", nullable = false)
  private boolean prohibited;
}
