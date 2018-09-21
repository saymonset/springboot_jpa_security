package com.apress.prospring5.ch6.jdbc.dao;

import com.apress.prospring5.ch6.entities.Singer;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by simon on 20/09/18.
 */
public interface SingerDao {

    List<Singer> findAll();
    List<Singer> findByFirstName(String firstName);
    String findLastNameById(Long id);
    String findFirstNameById(Long id);
    void insert(Singer singer);
    void update(Singer singer);
    void delete(Long singerId);
    List<Singer> findAllWithDetail();
    void insertWithDetail(Singer singer);
    void setDataSource(DataSource dataSource);


    String findNameById(Long id);


    List<Singer> findAllWithAlbums();

    void insertWithAlbum(Singer singer);
}
