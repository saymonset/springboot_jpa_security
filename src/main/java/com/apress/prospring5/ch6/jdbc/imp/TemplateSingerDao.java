package com.apress.prospring5.ch6.jdbc.imp;

import
        com.apress.prospring5.ch6.jdbc.dao.SingerDao;
import com.apress.prospring5.ch6.entities.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import  org.springframework.beans.factory.BeanCreationException;
import  org.springframework.beans.factory.InitializingBean;
import  org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
/**
 * Created by simon on 20/09/18.
 */
//@Component
public class TemplateSingerDao implements SingerDao, InitializingBean {
    private static Logger logger =
            LoggerFactory.getLogger(TemplateSingerDao.class);
    private JdbcTemplate jdbcTemplate;

   // @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override public String findNameById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT FIRST_NAME || ' ' || LAST_NAME FROM journal.SINGER WHERE ID = ?",
                new Object[]{id}, String.class);
    }

    @Override
    public String findFirstNameById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT FIRST_NAME FROM journal.SINGER WHERE id = ?",
                new Object[]{id}, String.class);
    }

    // Unimplemented methods, add implementations and tests as practice
    @Override public List<Singer> findAll() {

       return null;
    }

     public List<Singer> findByFirstName(String firstName) {
        return null;
    }

    @Override public String findLastNameById(Long id) {
    return  null;
    }

    @Override public void insert(Singer singer) {

    }

    @Override public void update(Singer singer) {

    }

    @Override public void delete(Long singerId) {

    }

    @Override
    public List<Singer> findAllWithDetail() {
        return null;
    }

    @Override
    public void insertWithDetail(Singer singer) {

    }

    @Override
    public void setDataSource(DataSource dataSource) {

    }

    @Override public List<Singer> findAllWithAlbums() {
          return null;
    }

    @Override public void insertWithAlbum(Singer singer) {

    }

    @Override public void afterPropertiesSet() throws Exception {
        logger.info("Inicializando---------------------------");
        if (jdbcTemplate == null) {
            throw new BeanCreationException("Null JdbcTemplate on SingerDao");
        }
    }
}