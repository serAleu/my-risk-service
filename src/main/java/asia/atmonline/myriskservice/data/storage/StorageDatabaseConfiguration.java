package asia.atmonline.myriskservice.data.storage;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(
    basePackages = "asia.atmonline.myriskservice.data.storage.*",
    entityManagerFactoryRef = "storageEntityManager"
)
public class StorageDatabaseConfiguration {

  @Bean("storageDataSourceProperties")
  @ConfigurationProperties("datasource.storage")
  public DataSourceProperties storageDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "storageDatasource")
  public DataSource storageDatasource(@Qualifier("storageDataSourceProperties") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  @Bean("storageEntityManager")
  public LocalContainerEntityManagerFactoryBean storageEntityManager(@Qualifier("storageDatasource") DataSource storageDataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(storageDataSource);
    em.setPackagesToScan("asia.atmonline.myriskservice.data.storage.*");
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    return em;
  }
}