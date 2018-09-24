package com.apress.spring;

/**
 * Created by simon on 21/09/18.
 */
import com.apress.prospring5.ch6.config.AppConfig;
import com.apress.prospring5.ch6.config.DbConfigTemplateName;
import com.apress.prospring5.ch6.entities.Album;
import com.apress.prospring5.ch6.entities.Singer;
import com.apress.prospring5.ch6.jdbc.dao.AnnotationSingerDao;
import com.apress.prospring5.ch6.jdbc.dao.SingerDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by simon on 21/09/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnotationJdbcTest {
    private static Logger logger = LoggerFactory.getLogger(AnnotationJdbcTest.class);


    private GenericApplicationContext ctx;

    private AnnotationSingerDao singerDao;
    @Before
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        singerDao = ctx.getBean(AnnotationSingerDao.class);
        assertNotNull(singerDao);
    }
    @Test
    public void testFindAll() {
        List<Singer> singers = singerDao.findAll();
        assertTrue(singers.size() == 3);
        singers.forEach(singer -> {
            logger.info(singer.getFirstName());
            if (singer.getAlbums() != null) {
                for (Album album : singer.getAlbums()) {
                    System.out.println("\t--> " +  album);
                }
            }
        });
        ctx.close();
    }

    @Test
    public void testFindByFirstName() {
        List<Singer> singers = singerDao.findByFirstName("Eric");
        assertTrue(singers.size() == 1);
        listSingers(singers);
        ctx.close();
    }

    @Test
    public void testSingerUpdate() {
        Singer singer = new Singer();
        singer.setId(1L);
        singer.setFirstName("John Clayton");
        singer.setLastName("Mayer");
        singer.setBirthDate(new Date(
                (new GregorianCalendar(1977, 9, 16)).getTime().getTime()));
        singerDao.update(singer);
        List<Singer> singers =  singerDao.findAll();
        listSingers(singers);
    }

    @Test
    public void testSingerInsert(){
        Singer singer = new Singer();
        singer.setFirstName("Solangel");
        singer.setLastName("Elena");
        singer.setBirthDate(new Date(
                (new GregorianCalendar(1991, 1, 17)).getTime().getTime()));
        singerDao.insert(singer);
        List<Singer> singers = singerDao.findAll();
        listSingers(singers);
    }


    @Test
    public void testSingerInsertWithAlbum(){
        Singer singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(new Date(
                (new GregorianCalendar(1940, 8, 16)).getTime().getTime()));
        Album album = new Album();
        album.setTitle("My Kind of Blues");
        album.setReleaseDate(new Date(
                (new GregorianCalendar(1961, 7, 18)).getTime().getTime()));
        singer.addAbum(album);
        album = new Album();
        album.setTitle("A Heart Full  of Blues");
        album.setReleaseDate(new Date(
                (new GregorianCalendar(1962, 3, 20)).getTime().getTime()));
        singer.addAbum(album);
        singerDao.insertWithAlbum(singer);
        List<Singer> singers = singerDao.findAllWithAlbums();
        listSingers(singers);
    }

    private void listSingers(List<Singer> singers){
        singers.forEach(singer -> {
            System.out.println(singer);
            if (singer.getAlbums() != null) {
                for (Album album : singer.getAlbums()) {
                    System.out.println("\t--> " +  album);
                }
            }
        });
    }


    @After
    public void tearDown() {
        ctx.close();
    }

}
