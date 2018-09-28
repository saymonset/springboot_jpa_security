package com.apress.prospring5.ch8.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

 import
        javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import  java.util.Iterator;
import  java.util.List;
/**
 * Created by simon on 26/09/18.
 */
@Service("singerSummaryUntype")
@Repository
@Transactional
public class SingerSummaryUntypeImpl {

    @PersistenceContext
    private EntityManager  em;


    private static Logger logger =
            LoggerFactory.getLogger(SingerSummaryUntypeImpl.class);


    @Transactional(readOnly =  true)
    public void displayAllSingerSummary() {
        List result = em.createQuery( "select  s.firstName, s.lastName, a.title from Singer s "
                + "left join s.albums a "
                + "where a.releaseDate=(select max(a2.releaseDate) "
                + "from Album a2 where a2.singer.id = s.id)")
                .getResultList();
        int count = 0;
        for (Iterator i = result.iterator(); i.hasNext(); ) {
            Object[] values = (Object[]) i.next();
            logger.info(++count + ": " + values[0] + ", "
                    + values[1] + ", " + values[2]);
        }
    }

}
