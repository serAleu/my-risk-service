package asia.atmonline.myriskservice.services.score.config;

import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DbScoreConfig {

  @Value("${score.datasource.jdbcDriver}")
  private String scoreDsJdbcDriver;
  @Value("${score.datasource.url}")
  private String scoreDsUrl;
  @Value("${score.datasource.port}")
  private String scoreDsPort;
  @Value("${score.datasource.database}")
  private String scoreDsDatabase;
  @Value("${score.datasource.username}")
  private String scoreDsUsername;
  @Value("${score.datasource.password}")
  private String scoreDsPassword;

  @Bean(name = "dbMy")
  public PGSimpleDataSource dataSourceMyReplica() {
    PGSimpleDataSource ds = new PGSimpleDataSource();
    ds.setURL(
        createUrlString(scoreDsJdbcDriver, scoreDsUrl, scoreDsPort, scoreDsDatabase));
    ds.setUser(scoreDsUsername);
    ds.setPassword(scoreDsPassword);
    return ds;
  }

  @Bean(name = "namedParameterJdbcTemplateMy")
  public NamedParameterJdbcTemplate jdbcTemplateMyReplica(@Qualifier("dbMy") DataSource ds) {
    return new NamedParameterJdbcTemplate(ds);
  }

  private String createUrlString(String jdbcDriver, String url, String port, String database) {
    return jdbcDriver + "://" + url + ":" + port + "/" + database;
  }
}