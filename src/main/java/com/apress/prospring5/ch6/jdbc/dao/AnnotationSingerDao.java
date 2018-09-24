package com.apress.prospring5.ch6.jdbc.dao;

import com.apress.prospring5.ch6.entities.Singer;

import java.util.List;

/**
 * Created by simon on 21/09/18.
 */
public interface AnnotationSingerDao {
    List<Singer> findAll();
    List<Singer> findByFirstName(String firstName);
    String findNameById(Long id);
    String findLastNameById(Long id);
    String findFirstNameById(Long id);
    List<Singer> findAllWithAlbums();

  void  insert(Singer singer);
    void  update(Singer singer);
    void delete(Long singerId);
    void insertWithAlbum(Singer singer);
}
