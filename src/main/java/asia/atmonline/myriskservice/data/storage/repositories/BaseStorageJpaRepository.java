package asia.atmonline.myriskservice.data.storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseStorageJpaRepository<T> extends JpaRepository<T, Long> {
}
