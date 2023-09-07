package asia.atmonline.myriskservice.data.storage.entity.borrower;

import asia.atmonline.myriskservice.data.storage.entity.BaseStorageEntity;
import asia.atmonline.myriskservice.data.storage.entity.application.CreditApplication;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "borrower_external_data")
public class BorrowerExternalData extends BaseStorageEntity {

  @Column(name = "requested_at")
  private LocalDateTime requestedAt;

  @Column(name = "data_type")
  private String type;

  @Column(name = "path")
  private String path;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "application_id")
  private CreditApplication application;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "borrower_id")
  private Borrower borrower;
}
