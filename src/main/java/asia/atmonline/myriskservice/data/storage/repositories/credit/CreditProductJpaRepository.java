package asia.atmonline.myriskservice.data.storage.repositories.credit;

import asia.atmonline.myriskservice.data.storage.entity.credit.CreditProduct;
import asia.atmonline.myriskservice.data.storage.repositories.BaseStorageJpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditProductJpaRepository extends BaseStorageJpaRepository<CreditProduct> {

  @Query(value = "select cp from CreditProduct cp where cp.code = ?1 and cp.active = true")
  Optional<CreditProduct> findProductByCode(String code);
}
