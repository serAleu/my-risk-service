package asia.atmonline.myriskservice.config.score;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@Slf4j
public class RiskScoreDatabaseConfiguration {

  @Value("${score.ds.jdbcDriver}")
  private String scoreDsJdbcDriver;
  @Value("${score.ds.url}")
  private String scoreDsUrl;
  @Value("${score.ds.port}")
  private String scoreDsPort;
  @Value("${score.ds.database}")
  private String scoreDsDatabase;
  @Value("${score.ds.username}")
  private String scoreDsUsername;
  @Value("${score.ds.password}")
  private String scoreDsPassword;

  @Bean(name = "dbMy")
  public HikariDataSource dataSourceMyReplica() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(scoreDsJdbcDriver);
    config.setJdbcUrl(createUrlString(scoreDsUrl, scoreDsPort, scoreDsDatabase));
    config.setUsername(scoreDsUsername);
    config.setPassword(scoreDsPassword);
    return new HikariDataSource(config);
  }

  @Bean(name = "namedParameterJdbcTemplateMy")
  public NamedParameterJdbcTemplate jdbcTemplateMyReplica(@Qualifier("dbMy") DataSource ds) {
    return new NamedParameterJdbcTemplate(ds);
  }

  private String createUrlString(String url, String port, String database) {
    return "jdbc:postgresql://" + url + ":" + port + "/" + database;
  }
}