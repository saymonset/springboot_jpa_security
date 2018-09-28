package com.apress.spring;

import com.apress.prospring5.ch6.jdbc.config.DbConfigTemplateName;
import com.apress.prospring5.ch6.entities.Album;
import com.apress.prospring5.ch6.entities.Singer;
import com.apress.prospring5.ch6.jdbc.dao.SingerDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by simon on 21/09/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JdbctemplateTest {


    private static Logger logger = LoggerFactory.getLogger(JdbctemplateTest.class);

    @Test
    public void testOne() throws SQLException {
        GenericApplicationContext ctx =
                new AnnotationConfigApplicationContext(DbConfigTemplateName.class);
        SingerDao singerDao = ctx.getBean(SingerDao.class);
        String singerName = singerDao.findFirstNameById(1l);
        logger.info("singerName = " + singerName);
        ctx.close();
    }

    @Test
    public void testRowMapper() {
        GenericApplicationContext ctx  =
                new AnnotationConfigApplicationContext(DbConfigTemplateName.class);
        SingerDao singerDao = ctx.getBean(SingerDao.class);
        assertNotNull(singerDao);
        List<Singer> singers = singerDao.findAll();
        assertTrue(singers.size() ==  singers.size());
        singers.forEach(singer -> {
            logger.info("saymon = " + singer.getFirstName());
            if (singer.getAlbums() != null) {
                for (Album album   :
                        singer.getAlbums()) {
                    logger.info("---" +  album);
                }
            }
        });
        ctx.close();
    }

    @Test
    public void testResultSetExtractor() {
        GenericApplicationContext ctx  =
                new AnnotationConfigApplicationContext(DbConfigTemplateName.class);
        SingerDao singerDao = ctx.getBean(SingerDao.class);
        assertNotNull(singerDao);
        List<Singer> singers = singerDao.findAllWithAlbums();
        assertTrue(singers.size() ==  singers.size());
        singers.forEach(singer -> {
            logger.info("chuo = " + singer.getFirstName());
            if (singer.getAlbums() != null) {
                for (Album album   :
                        singer.getAlbums()) {
                    logger.info("\t--> " +  album);
                }
            }
        });
        ctx.close();
    }
}

