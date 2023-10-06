package asia.atmonline.myriskservice.data.storage.entity;

import asia.atmonline.myriskservice.data.storage.entity.borrower.Borrower;
import asia.atmonline.myriskservice.data.storage.entity.credit.CreditProduct;
import asia.atmonline.myriskservice.enums.application.CreditApplicationSource;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseCreditEntity extends BaseStorageEntity {

  @Column(name = "external_id")
  private String externalId;

  @Column(name = "credit_product_id", nullable = false)
  private Long creditProductId;

  @Column(name = "credit_product_revision", nullable = false)
  private Long creditProductRevision;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "borrower_id", nullable = false)
  private Borrower borrower;

  @Enumerated(EnumType.STRING)
  @Column(name = "source", updatable = false)
  private CreditApplicationSource source;

  @Column(name = "contract_number", unique = true)
  private String contractNumber;

//  @JsonIgnore
//  @XmlTransient
//  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
//  @JoinTable(inverseJoinColumns = @JoinColumn(name = "tag_id"))
//  private Set<Tag> tags;

//  @JsonIgnore
//  @OrderBy("date DESC")
//  @XmlElement(name = "comments")
//  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  @JoinTable(inverseJoinColumns = @JoinColumn(name = "comment_id"))
//  @BatchSize(size = DEFAULT_BATCH_SIZE)
//  private Set<Comment> comments;

  @JsonIgnore
  @Transient
  private transient CreditProduct creditProduct;

  @Transient
  public CreditProduct getCreditProduct() {
    if (this.creditProduct != null) {
      return creditProduct;
    }
//    creditProduct = ApplicationContextProvider
//        .getApplicationContext().getBean(AuditedEntityRepository.class)
//        .getEntityWithCache(CreditProduct.class, getCreditProductId(), getCreditProductRevision());
    return creditProduct;
  }

//  @Override
//  public Set<Comment> getComments() {
//    // проверка на null для импорта
//    return comments == null || comments.isEmpty() ? comments = new HashSet<>() : comments;
//  }
}
