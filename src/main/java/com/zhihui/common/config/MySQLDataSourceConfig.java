package com.zhihui.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author LDZ
 * @date 2020-02-17 10:10
 */
@Configuration
public class MySQLDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.other")
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }
}
