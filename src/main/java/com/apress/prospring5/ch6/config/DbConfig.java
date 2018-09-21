package com.apress.prospring5.ch6.config;

/**
 * Created by simon on 20/09/18.
 */

import com.apress.prospring5.ch6.jdbc.dao.SingerDao;
import com.apress.prospring5.ch6.jdbc.dao.imp.TemplateSingerDao;
import
        org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import  org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import  org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class DbConfig {
    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer
    propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Lazy
    @Bean
    public DataSource dataSourcebad() {
        try {
            SimpleDriverDataSource dataSource =
                    new SimpleDriverDataSource();
            Class<? extends Driver> driver =
                    (Class<? extends Driver>)  Class.forName(driverClassName);
            dataSource.setDriverClass(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (Exception e) {
            return null;
        }
    }

    @Bean
   // @Profile("test")
    public DataSource dataSource0() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean public JdbcTemplate jdbcTemplate0(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource0());
        return jdbcTemplate;
    }

    @Bean
    public SingerDao singerDao(){
        TemplateSingerDao dao  =  new TemplateSingerDao();

        dao.setJdbcTemplate(jdbcTemplate0());
        return  dao;
    }
}
