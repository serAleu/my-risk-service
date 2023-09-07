package asia.atmonline.myriskservice.data.storage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@MappedSuperclass
@Getter
@Setter
public class BaseStorageEntity implements Serializable {

  public static final int DEFAULT_BATCH_SIZE = 500;

  public static final int DEFAULT_AMOUNT_SCALE = 0;
  public static final int AMOUNT_SCALE_4_SIGNS = 4;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "deleted", nullable = false, columnDefinition = "bool default false")
  private boolean deleted;

  @Override
  public int hashCode() {
    if (id == null) {
      return super.hashCode();
    }
    final int prime = 31;
    int result = 1;
    result = prime * result + id.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    // need to get superclass while comparing entity with Hibernate proxy
    // Super class is applicable because we are use CGLIB proxying.
    Class<?> objClass = (obj instanceof HibernateProxy)
        ? obj.getClass().getSuperclass()
        : obj.getClass();
    if (getClass() != objClass) {
      return false;
    }
    BaseStorageEntity other = (BaseStorageEntity) obj;
    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }
    return true;
  }
}
