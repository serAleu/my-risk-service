package asia.atmonline.myriskservice.data.storage.entity.borrower;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import asia.atmonline.myriskservice.enums.borrower.BorrowerAdditionalPhoneType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "borrower_additional_phone_data", schema = "my-back")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class BorrowerAdditionalPhone extends BaseStorageEntity {

  @Column(name = "borrower_id", nullable = false, updatable = false)
  private Long borrowerId;

  @Column(name = "value", nullable = false)
  private String value;

  @Column(name = "type", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private BorrowerAdditionalPhoneType type;

  @Column(name = "data_source")
  private String dataSource;

//  @ManyToOne(fetch = FetchType.EAGER)
//  @JoinColumn(name = "created_by")
//  private BackOfficeUserAccount createdBy;

  @Column(name = "created_at")
  private LocalDateTime createdAt = LocalDateTime.now();

}
