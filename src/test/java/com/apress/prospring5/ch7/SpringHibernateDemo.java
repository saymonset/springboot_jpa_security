package com.apress.prospring5.ch7;
 import
        com.apress.prospring5.ch7.config.AppConfig;
import com.apress.prospring5.ch7.dao.SingerDao;
 import  com.apress.prospring5.ch7.entities.Singer;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.context.annotation.AnnotationConfigApplicationContext;
 import org.springframework.context.support.GenericApplicationContext;

 import java.util.List;

/**
 * Created by simon on 24/09/18.
 */
public class SpringHibernateDemo {
    private static Logger logger =
            LoggerFactory.getLogger(SpringHibernateDemo.class);
    public static void main(String... args) {
        GenericApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppConfig.class);
        SingerDao singerDao = ctx.getBean(SingerDao.class);
      //  singerDao.delete(singer);
        listSingers(singerDao.findAll());
        ctx.close();
    }
    private static void listSingers(List<Singer> singers) {
        logger.info(" ---- Listing singers:");
        for (Singer singer : singers) {
            logger.info(singer.toString());
        }
    }
}
