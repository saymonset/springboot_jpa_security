package com.apress.prospring5.ch7;
 import
        com.apress.prospring5.ch7.config.AppConfig;
import com.apress.prospring5.ch7.dao.SingerDao;
 import com.apress.prospring5.ch7.entities.Album;
 import com.apress.prospring5.ch7.entities.Instrument;
 import  com.apress.prospring5.ch7.entities.Singer;
 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.context.annotation.AnnotationConfigApplicationContext;
 import org.springframework.context.support.GenericApplicationContext;

 import java.util.Date;
 import java.util.GregorianCalendar;
 import java.util.List;
 import java.util.Set;

 import static junit.framework.TestCase.assertNotNull;
 import static org.junit.Assert.assertEquals;

/**
 * Created by simon on 24/09/18.
 */
public class SpringHibernateDemo {
    private static Logger logger =
            LoggerFactory.getLogger(SpringHibernateDemo.class);
    private GenericApplicationContext ctx;
    private SingerDao singerDao;
    @Before
    public void setUp(){
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        singerDao = ctx.getBean(SingerDao.class);
        assertNotNull(singerDao);
    }

    @Test
    public void testInsert(){
        Singer singer = new Singer();
        singer.setFirstName("BB12");
        singer.setLastName("King12");
        singer.setBirthDate(new Date(
                (new GregorianCalendar(1940, 8, 16)).getTime().getTime()));
        Album album = new Album();
        album.setTitle("My Kind of Blues22");
        album.setReleaseDate(new java.sql.Date(
                (new GregorianCalendar(1961, 7, 18)).getTime().getTime()));
        singer.addAbum(album);
        album = new Album();
        album.setTitle("A Heart Full of Blues22");
        album.setReleaseDate(new java.sql.Date(
                (new GregorianCalendar(1962, 3, 20)).getTime().getTime()));
        singer.addAbum(album);
        singerDao.save(singer);
        assertNotNull(singer.getId());
        List<Singer> singers = singerDao.findAllWithAlbum();
        assertEquals(7, singers.size());
        listSingersWithAlbum(singers);
    }

    @Test
    public void testFindAll(){
        List<Singer> singers = singerDao.findAll();
        assertEquals(3, singers.size());
        listSingers(singers);
    }
    @Test
    public void testFindAllWithAlbum(){
        List<Singer> singers = singerDao.findAllWithAlbum();
        assertEquals(3, singers.size());
        listSingersWithAlbum(singers);
    }
    @Test
    public void testFindByID(){
        Singer singer = singerDao.findById(1L);
        assertNotNull(singer);
        logger.info(singer.toString());
    }
    private static void listSingers(List<Singer> singers) {
        logger.info(" ---- Listing singers:");
        for (Singer singer : singers) {
            logger.info(singer.toString());
        }
    }

    private static void listSingersWithAlbum(List<Singer> singers) {
        logger.info(" ---- Listing singers with instruments:");
        for (Singer singer : singers) {
            logger.info(singer.toString());
            if (singer.getAlbums() != null) {
                for (Album album :
                        singer.getAlbums()) {
                    logger.info("\t" + album.toString());
                }
            }
            if (singer.getInstruments() != null) {
                for (Instrument instrument : singer.getInstruments()) {
                    logger.info("\tInstrument: " + instrument.getInstrumentId());
                }
            }
        }
    }
    @After
    public void tearDown(){
        ctx.close();
    }
}
