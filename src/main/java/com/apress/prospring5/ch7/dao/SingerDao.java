package com.apress.prospring5.ch7.dao;

import com.apress.prospring5.ch7.entities.Singer;

import java.util.List;

/**
 * Created by simon on 24/09/18.
 */
public interface SingerDao {
    List<Singer> findAll();
    List<Singer> findAllWithAlbum();
    Singer findById(Long id);
    Singer save(Singer contact);
    void delete(Singer contact);
}
