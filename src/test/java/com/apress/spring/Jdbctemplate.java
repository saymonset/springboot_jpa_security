package com.apress.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by simon on 21/09/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Jdbctemplate {


    private static Logger logger = LoggerFactory.getLogger(Jdbctemplate.class);

    @Test
    public void testOne() throws SQLException {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:drivermanager-cfg-01.xml");
        ctx.refresh();
        DataSource dataSource = ctx.getBean("dataSource", DataSource.class);
        assertNotNull(dataSource);
        logger.info("Hola mundo");
        ctx.close();
    }
}
