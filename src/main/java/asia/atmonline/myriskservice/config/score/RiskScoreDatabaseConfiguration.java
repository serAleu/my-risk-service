package asia.atmonline.myriskservice.config.score;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
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

//  @Bean(name = "dbMy")
//  public PGSimpleDataSource dataSourceMyReplica() {
//    PGSimpleDataSource ds = new PGSimpleDataSource();
//    ds.setURL(
//        createUrlString(scoreDsJdbcDriver, scoreDsUrl, scoreDsPort, scoreDsDatabase));
//    ds.setUser(scoreDsUsername);
//    ds.setPassword(scoreDsPassword);
//    return ds;
//  }

  @Bean(name = "dbMy")
  public DriverManagerDataSource dataSourceMyReplica() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(scoreDsJdbcDriver);
    ds.setUrl(createUrlString(scoreDsJdbcDriver, scoreDsUrl, scoreDsPort, scoreDsDatabase));
    ds.setUsername(scoreDsUsername);
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