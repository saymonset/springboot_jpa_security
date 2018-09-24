package com.apress.prospring5.ch6.jdbc.config;

/**
 * Created by simon on 21/09/18.
 */

import com.apress.prospring5.ch6.jdbc.dao.SingerDao;
import com.apress.prospring5.ch6.jdbc.imp.NamedParameterJdbcTemplateSingerDao;
import com.apress.prospring5.ch6.jdbc.imp.TemplateSingerDao;
import
        org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import  org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import  org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class DbConfigTemplateName {
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
    public DataSource dataSource1() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean public NamedParameterJdbcTemplate namedParameterJdbcTemplate(){
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource1());

        return namedParameterJdbcTemplate;
    }

    @Bean
    public SingerDao singerDao(){
        NamedParameterJdbcTemplateSingerDao dao  =  new NamedParameterJdbcTemplateSingerDao();

        dao.setNamedParameterJdbcTemplate(namedParameterJdbcTemplate());
        return  dao;
    }
}
