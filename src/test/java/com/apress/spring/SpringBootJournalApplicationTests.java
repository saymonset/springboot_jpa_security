package com.apress.spring;

import com.apress.prospring5.ch6.config.DbConfig;
import com.apress.prospring5.ch6.config.TemplateJdbcConfig;
import com.apress.prospring5.ch6.entities.Singer;
import com.apress.prospring5.ch6.jdbc.dao.SingerDao;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootJournalApplicationTests {

	@Test
	public void contextLoads() {
	}

	private static Logger logger = LoggerFactory.getLogger(SpringBootJournalApplicationTests.class);
	@Ignore
	@Test
	public void testOne() throws SQLException {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:drivermanager-cfg-01.xml");
		ctx.refresh();
		DataSource dataSource = ctx.getBean("dataSource", DataSource.class);
		assertNotNull(dataSource);
		testDataSource(dataSource);
		ctx.close();
	}

//	@Test
	public void testDBCP() throws SQLException {
	/*	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:drivermanager-cfg-01.xml");
		ctx.refresh();
		DataSource dataSource = ctx.getBean("dataSourcedbcp2", DataSource.class);
		assertNotNull(dataSource);
		testDataSource(dataSource);
		ctx.close();*/
	}

  @Ignore
	@Test
	public void testTwo() throws SQLException {
		GenericApplicationContext ctx =
				new AnnotationConfigApplicationContext(DbConfig.class);
		DataSource dataSource = ctx.getBean("dataSource2", DataSource.class);
		assertNotNull(dataSource);
		//testDataSource(dataSource);
		ctx.close();
	}

	@Test
	public void testTree() throws SQLException {
		GenericApplicationContext ctx =
				new AnnotationConfigApplicationContext(TemplateJdbcConfig.class);
		//DataSource dataSource = ctx.getBean("dataSource3", DataSource.class);
		//ctx.refresh();
		/*DataSource dataSource = (DataSource) ctx.getBean("dataSource3");
		assertNotNull(dataSource);
        testDataSource(  dataSource);*/
		SingerDao singerDao = (SingerDao)ctx.getBean("singerDao");
		assertNotNull(singerDao);
		//singerDao.setDataSource(dataSource);
		testDao( singerDao);
		ctx.close();
	}

	private void testDao(SingerDao singerDao) {
		assertNotNull(singerDao);
		String singerName = singerDao.findFirstNameById(1l);
		List<Singer> lit =  singerDao.findAll();
		if (lit != null && lit.size() > 0){
			lit.stream().forEach(a->{
				logger.info("first name = " + a.getFirstName());
			});
		}
		logger.info("***** = " + singerName);
		logger.info("***** = " + singerName);

		//assertTrue("John Mayer".equals(singerName));
	}

	private void testDataSource(DataSource dataSource) throws SQLException{
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			PreparedStatement statement =
					connection.prepareStatement("SELECT 1");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())  {
				int mockVal = resultSet.getInt("1");
				assertTrue(mockVal== 1);
			}
			statement.close();
		} catch (Exception e) {
			logger.debug("Something  unexpected happened.",  e);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}


}
