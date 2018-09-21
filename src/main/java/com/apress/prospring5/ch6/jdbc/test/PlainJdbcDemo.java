package com.apress.prospring5.ch6.jdbc.test;
import
        com.apress.prospring5.ch6.jdbc.ConnectionJdbc;
 import com.apress.prospring5.ch6.jdbc.dao.SingerDao;
import  com.apress.prospring5.ch6.entities.Singer;
import  org.slf4j.Logger;
import  org.slf4j.LoggerFactory;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;
/**
 * Created by simon on 20/09/18.
 */
public class PlainJdbcDemo {
    private static SingerDao singerDao = new ConnectionJdbc();
    private static Logger logger = LoggerFactory.getLogger(PlainJdbcDemo.class);
    public static void main(String... args) {
        logger.info("Listing initial singer data:");
        listAllSingers();
        logger.info("-------------");
        logger.info("Insert a  new singer");
        Singer singer = new Singer();
        singer.setFirstName("Ed");
        singer.setLastName("Sheeran");
        singer.setBirthDate(new  Date
                ((new GregorianCalendar(1991,  2, 1991)).getTime().getTime()));
        singerDao.insert(singer);
        logger.info("Listing singer data after new singer created:");
        listAllSingers();
        logger.info("-------------");
        logger.info("Deleting the previous created singer");
        singerDao.delete(singer.getId());
        logger.info("Listing singer data after new singer deleted:");
        listAllSingers();
    }
    private static void listAllSingers() {
        List<Singer> singers = singerDao.findAll();
        singers.stream().forEach(a->{
            logger.info("Una bestia saymon  = " + a.toString());
        });
       /* for (Singer singer: singers) {
            logger.info(singer.toString());
        }*/
    }
}
