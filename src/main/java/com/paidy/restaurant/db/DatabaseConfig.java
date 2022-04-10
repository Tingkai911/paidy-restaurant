package com.paidy.restaurant.db;

import java.util.Base64;
import javax.sql.DataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.paidy.restaurant.db.mapper")
public class DatabaseConfig {
  @Bean
  @ConfigurationProperties(prefix = "spring.datasource")
  public DataSourceProperties dataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.hikari")
  public DataSource dataSource(@Value("${spring.datasource.drowssap}") String drowssap) {
    DataSourceProperties emmsDataSourceProperties = dataSourceProperties();
    byte[] decodedBytes = Base64.getDecoder().decode(drowssap);
    emmsDataSourceProperties.setPassword(new String(decodedBytes));
    return emmsDataSourceProperties.initializeDataSourceBuilder().build();
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory(
      @Value("${spring.datasource.drowssap}") String drowssap) throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource(drowssap));
    return sqlSessionFactoryBean.getObject();
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(
      @Value("${spring.datasource.drowssap}") String drowssap) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory(drowssap), ExecutorType.BATCH);
  }
}
