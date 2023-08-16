package asia.atmonline.myriskservice.config;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

  @Value("${spring.flyway.baselineOnMigrate}")
  private Boolean baselineOnMigrate;
  private final DataSource postgresDatasource;

  public FlywayConfig(@Qualifier("postgresDatasource") DataSource postgresDatasource) {
    this.postgresDatasource = postgresDatasource;
  }

  @PostConstruct
  public void runMigration() {
    Flyway.configure()
        .dataSource(postgresDatasource)
        .locations("db.migration")
        .baselineOnMigrate(baselineOnMigrate)
        .load()
        .migrate();
  }
}
