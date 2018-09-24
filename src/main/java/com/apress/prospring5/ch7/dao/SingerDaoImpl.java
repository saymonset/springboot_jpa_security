package com.apress.prospring5.ch7.dao;

import com.apress.prospring5.ch6.entities.Singer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created by simon on 24/09/18.
 */
@Transactional
@Repository("singerDao")
public class SingerDaoImpl implements SingerDao {

    private static final Log logger = LogFactory.getLog(SingerDaoImpl.class);
    private SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Transactional(readOnly = true)
    public List<com.apress.prospring5.ch7.entities.Singer> findAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Singer s").list();
    }

    @Override
    public List<com.apress.prospring5.ch7.entities.Singer> findAllWithAlbum() {
        return null;
    }

    @Override
    public com.apress.prospring5.ch7.entities.Singer findById(Long id) {
        return null;
    }

    @Override
    public com.apress.prospring5.ch7.entities.Singer save(com.apress.prospring5.ch7.entities.Singer contact) {
        return null;
    }

    @Override
    public void delete(com.apress.prospring5.ch7.entities.Singer contact) {

    }
}
