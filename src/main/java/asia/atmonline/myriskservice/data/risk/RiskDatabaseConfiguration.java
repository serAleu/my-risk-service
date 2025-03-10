package asia.atmonline.myriskservice.data.risk;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(
    basePackages = "asia.atmonline.myriskservice.data.risk.*",
    entityManagerFactoryRef = "riskEntityManager"
)
public class RiskDatabaseConfiguration {

  @Bean("riskDataSourceProperties")
  @ConfigurationProperties("datasource.risk")
  public DataSourceProperties dataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "postgresDatasource")
  @FlywayDataSource
  @Primary
  public DataSource postgresDatasource(@Qualifier("riskDataSourceProperties") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
  }

  @Bean("riskEntityManager")
  @Primary
  public LocalContainerEntityManagerFactoryBean riskEntityManager(@Qualifier("postgresDatasource") DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("asia.atmonline.myriskservice.data.risk.*");
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    return em;
  }
}
