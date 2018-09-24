package com.apress.prospring5.ch6.jdbc.imp;

import com.apress.prospring5.ch6.entities.Album;
import com.apress.prospring5.ch6.entities.Singer;
import com.apress.prospring5.ch6.jdbc.dao.AnnotationSingerDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by simon on 21/09/18.
 */
@Repository("annotationSingerDao")
public class AnnotationJdbcSingerDao implements AnnotationSingerDao {
    private static final Log logger =
            LogFactory.getLog(AnnotationJdbcSingerDao.class);
    //@Resource(name="sellectAllSinger")
    private SelectAllSingers selectAllSingers;
    private UpdateSinger updateSinger;
    private SelectSingerByFirstName selectSingerByFirstName;
    private InsertSinger insertSinger;
    private DataSource dataSource;
    private InsertSingerAlbum insertSingerAlbum;
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource)  {

        this.dataSource = dataSource;
        this.selectAllSingers = new SelectAllSingers(dataSource);
        this.selectSingerByFirstName =
                new SelectSingerByFirstName(dataSource);
        this.updateSinger = new UpdateSinger(dataSource);
        this.insertSinger = new InsertSinger(dataSource);
    }
    public DataSource getDataSource()  {
        return dataSource;
    }
    @Override
    public List<Singer> findAll() {

        return selectAllSingers.execute();
    }

    @Override
    public List<Singer> findByFirstName(String firstName) {


        Map<String, Object> paramMap  =  new HashMap<>();
        paramMap.put("first_name", firstName);
        return selectSingerByFirstName.executeByNamedParam(paramMap);
    }

    @Override
    public String findNameById(Long id) {
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




    public List<Singer> findAllWithAlbums() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        String sql = "SELECT s.ID, s.FIRST_NAME, s.LAST_NAME, s.BIRTH_DATE" +
                ", a.ID AS album_id, a.TITLE, a.RELEASE_DATE FROM  journal.SINGER s " +
                "LEFT JOIN journal.ALBUM a ON s.ID = a.SINGER_ID";
        return jdbcTemplate.query(sql, new SingerWithAlbumExtractor());
    }

    private static final class SingerWithAlbumExtractor
            implements ResultSetExtractor<List<Singer>> {
        public List<Singer> extractData(ResultSet rs) throws
                SQLException, DataAccessException {
            Map<Long, Singer> map = new HashMap<>();
            Singer singer;
            while (rs.next())  {
                Long id = rs.getLong("id");
                singer = map.get(id);
                if (singer == null) {
                    singer = new Singer();
                    singer.setId(id);
                    singer.setFirstName(rs.getString("first_name"));
                    singer.setLastName(rs.getString("last_name"));
                    singer.setBirthDate(rs.getDate("birth_date"));
                    singer.setAlbums(new ArrayList<>());
                    map.put(id, singer);
                }
                Long albumId = rs.getLong("album_id");
                if (albumId > 0) {
                    Album album = new Album();
                    album.setId(albumId);
                    album.setSingerId(id);
                    album.setTitle(rs.getString("title"));
                    album.setReleaseDate(rs.getDate("release_date"));
                    singer.getAlbums().add(album);
                }
            }
            return new ArrayList<>(map.values());
        }
    }

    public void insert(Singer singer) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("FIRST_NAME", singer.getFirstName());
        paramMap.put("LAST_NAME", singer.getLastName());
        paramMap.put("BIRTH_DATE", singer.getBirthDate());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertSinger.updateByNamedParam(paramMap, keyHolder);
        singer.setId(keyHolder.getKey().longValue());
        logger.info("New singer inserted with id: " + singer.getId());
    }

    public void update(Singer singer)  {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("FIRST_NAME", singer.getFirstName());
        paramMap.put("LAST_NAME", singer.getLastName());
        paramMap.put("BIRTH_DATE", singer.getBirthDate());
        paramMap.put("ID", singer.getId());
        updateSinger.updateByNamedParam(paramMap);
        logger.info("Existing singer updated with  id: " + singer.getId());
    }
    @Override
    public void delete(Long singerId) {

    }

    public void insertWithAlbum(Singer singer) {
        insertSingerAlbum = new InsertSingerAlbum(dataSource);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("FIRST_NAME", singer.getFirstName());
        paramMap.put("LAST_NAME", singer.getLastName());
        paramMap.put("BIRTH_DATE", singer.getBirthDate());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertSinger.updateByNamedParam(paramMap, keyHolder);
        singer.setId(keyHolder.getKey().longValue());
        logger.info("New singer inserted with id: " +  singer.getId());
        List<Album>  albums =  singer.getAlbums();
        if (albums != null) {
            for (Album album : albums) {
                paramMap = new HashMap<>();
                paramMap.put("SINGER_ID", singer.getId());
                paramMap.put("TITLE", album.getTitle());
                paramMap.put("RELEASE_DATE", album.getReleaseDate());
                insertSingerAlbum.updateByNamedParam(paramMap);
            }
        }
        insertSingerAlbum.flush();
    }
}


