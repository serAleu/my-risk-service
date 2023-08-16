package asia.atmonline.myriskservice.config;

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
    basePackages = "asia.atmonline.myriskservice.data.*",
    entityManagerFactoryRef = "riskEntityManager"
)
public class PostgresDbConfiguration {

//  @Primary
//  @Bean(name = "postgresDatasource")
//  @ConfigurationProperties(prefix = "datasource")
//  @FlywayDataSource
//  public DataSource dataSource() {
//    DataSource dataSource = DataSourceBuilder.create().build();
//    return dataSource;
//  }

  @Bean("riskDataSourceProperties")
  @ConfigurationProperties("datasource")
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
  public LocalContainerEntityManagerFactoryBean riskEntityManager(@Qualifier("postgresDatasource") DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("asia.atmonline.myriskservice.data.*");
    em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    return em;
  }
}
