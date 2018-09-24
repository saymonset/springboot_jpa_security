package com.apress.prospring5.ch6.jdbc;

import com.apress.prospring5.ch6.jdbc.config.DbConfig;
import com.apress.prospring5.ch6.entities.Singer;
import com.apress.prospring5.ch6.jdbc.dao.SingerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by simon on 20/09/18.
 */
public class ConnectionJdbc implements SingerDao {
    private static Logger logger  =
            LoggerFactory.getLogger(ConnectionJdbc.class);
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch  (ClassNotFoundException ex)  {
            logger.error("Prblem  loadng DB dDiver!", ex);
        }
    }
    private Connection getConnection()  throws SQLException {


        /**
         *
         *
         * 	GenericApplicationContext ctx = new AnnotationConfigApplicationContext(
         KindergartenConfig.class,
         HighschoolConfig.class);

         FoodProviderService foodProviderService =
         ctx.getBean("foodProviderService", FoodProviderService.class);
         *
         * */

        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(DbConfig.class);
        javax.sql.DataSource dataSource = ctx.getBean("dataSource0",javax.sql.DataSource.class);
       /* GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("classpath:drivermanager-cfg-01.xml");
        ctx.refresh();

        DataSource dataSource = (DataSource) ctx.getBean("dataSource");*/

        return dataSource.getConnection();
      /*  return  DriverManager.getConnection(
                "jdbc:mysql://172.17.0.2:3306/journal?useSSL=true",
                "root", "123456");*/
    }
    private  void closeConnection(Connection connection)  {
        if (connection ==  null) {
            return;
        }
        try {
            connection.close();
        } catch  (SQLException ex)  {
            logger.error("Problem  closing  connection to the database!",ex);
        }
    }

    @Override
    public List<Singer> findAll() {
        List<Singer> result = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement =
                    connection.prepareStatement("select * from journal.SINGER");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())  {
                Singer singer = new Singer();
                singer.setId(resultSet.getLong("id"));
                singer.setFirstName(resultSet.getString("first_name"));
                singer.setLastName(resultSet.getString("last_name"));
                singer.setBirthDate(resultSet.getDate("birth_date"));
                result.add(singer);
            }
            statement.close();
        } catch  (SQLException ex) {
            logger.error("Problem when executing SELECT!",ex);
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public String findLastNameById(Long id) {
        return null;
    }

    @Override
    public String findFirstNameById(Long id) {
        return null;
    }

    @Override
    public void insert(Singer singer) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "insert into journal.SINGER (first_name,  last_name, birth_date)         values (?, ?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, singer.getFirstName());
            statement.setString(2, singer.getLastName());
            statement.setDate(3, singer.getBirthDate());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                singer.setId(generatedKeys.getLong(1));
            }
            statement.close();
        } catch (SQLException ex) {
            logger.error("Prblem  executing INSERT", ex);
        } finally {
            closeConnection(connection);
        }
    }

    @Override
    public void update(Singer singer) {

    }

    @Override
    public void delete(Long singerId) {
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement
                    ("delete from journal.SINGER where id=?");
            statement.setLong(1, singerId);
            statement.execute();
            statement.close();
        } catch (SQLException ex) {
            logger.error("Prblem executing DELETE", ex);
        } finally {
            closeConnection(connection);
        }
        }

    @Override
    public List<Singer> findAllWithDetail() {
        return null;
    }

    @Override
    public void insertWithDetail(Singer singer) {

    }

    @Override
    public String findNameById(Long id) {
        return null;
    }

    @Override
    public List<Singer> findAllWithAlbums() {
        return null;
    }

    @Override
    public void insertWithAlbum(Singer singer) {

    }

    @Override
    public void setDataSource(DataSource dataSource) {

    }
}
