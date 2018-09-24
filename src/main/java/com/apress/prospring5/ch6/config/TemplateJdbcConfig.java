package com.apress.prospring5.ch6.config;

import com.apress.prospring5.ch6.jdbc.dao.SingerDao;
import com.apress.prospring5.ch6.jdbc.imp.TemplateSingerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by simon on 20/09/18.
 */
@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class TemplateJdbcConfig {
    private static Logger logger =
            LoggerFactory.getLogger(TemplateJdbcConfig.class);
    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Bean
    // @Profile("test")
    public DataSource dataSource3() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }


    @Bean public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource3());
        return jdbcTemplate;
    }
    @Bean
    public SingerDao singerDao() {
        TemplateSingerDao dao = new TemplateSingerDao();
        dao.setJdbcTemplate(jdbcTemplate());
        return dao;
    }
}
