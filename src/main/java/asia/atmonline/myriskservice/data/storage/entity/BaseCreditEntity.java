package asia.atmonline.myriskservice.data.storage.entity;

import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.enums.application.CreditApplicationSource;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseCreditEntity extends BaseStorageEntity {

  @Column(name = "external_id", unique = true)
  private String externalId;

  @Column(name = "credit_product_id", nullable = false)
  private Long creditProductId;

  @Column(name = "credit_product_revision", nullable = false)
  private Long creditProductRevision;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "borrower_id", nullable = false)
  private Borrower borrower;

  @Enumerated(EnumType.STRING)
  @Column(name = "source", nullable = false, updatable = false)
  private CreditApplicationSource source;

  @Column(name = "contract_number", unique = true)
  private String contractNumber;

//  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//  @JoinTable(inverseJoinColumns = @JoinColumn(name = "tag_id"))
//  private Set<Tag> tags;
//
//  @OrderBy("date DESC")
//  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  @JoinTable(inverseJoinColumns = @JoinColumn(name = "comment_id"))
//  @BatchSize(size = DEFAULT_BATCH_SIZE)
//  private Set<Comment> comments;

//  @Transient
//  private transient CreditProduct creditProduct;

//  @Transient
//  public CreditProduct getCreditProduct() {
//    if (this.creditProduct != null) {
//      return creditProduct;
//    }
//    creditProduct = ApplicationContextProvider.getApplicationContext()
//        .getBean(AuditedEntityRepository.class)
//        .getEntityWithCache(CreditProduct.class, getCreditProductId(), getCreditProductRevision());
//    return creditProduct;
//  }
//  @Override
//  public Set<Comment> getComments() {
//    // проверка на null для импорта
//    return comments == null || comments.isEmpty() ? comments = new HashSet<>() : comments;
//  }
}
