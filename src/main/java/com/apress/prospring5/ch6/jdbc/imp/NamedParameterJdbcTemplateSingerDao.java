package com.apress.prospring5.ch6.jdbc.imp;

import com.apress.prospring5.ch6.entities.Album;
import com.apress.prospring5.ch6.entities.Singer;
import com.apress.prospring5.ch6.jdbc.dao.SingerDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

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

public class NamedParameterJdbcTemplateSingerDao implements
        SingerDao, InitializingBean {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<Singer> findAll() {
        String sql = "select ID, FIRST_NAME, LAST_NAME, BIRTH_DATE from journal.SINGER";
        return namedParameterJdbcTemplate.query(sql, (rs, rowNum)  -> {
            Singer singer = new Singer();
            singer.setId(rs.getLong("id"));
            singer.setFirstName(rs.getString("first_name"));
            singer.setLastName(rs.getString("last_name"));
            singer.setBirthDate(rs.getDate("birth_date"));
            return singer;
        });
    }


   // @Override
    public List<Singer> findAll_Obsoleta() {
        String sql = "select ID, FIRST_NAME, LAST_NAME, BIRTH_DATE from journal.SINGER";
        return namedParameterJdbcTemplate.query(sql, new SingerMapper());
    }


    private static final class SingerMapper
            implements RowMapper<Singer> {
        @Override
        public Singer mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            Singer singer = new Singer();
            singer.setId(rs.getLong("id"));
            singer.setFirstName(rs.getString("first_name"));
            singer.setLastName(rs.getString("last_name"));
            singer.setBirthDate(rs.getDate("birth_date"));
            return singer;
        }
    }

    @Override public String findNameById(Long id) {
        String sql = "SELECT FIRST_NAME ||' '|| LAST_NAME     FROM journal.SINGER WHERE id = :singerId";
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("singerId", id);
        return getNamedParameterJdbcTemplate().queryForObject(sql,
                namedParameters, String.class);
    }

    @Override
    public String findFirstNameById(Long id) {
        String sql = "SELECT FIRST_NAME       FROM journal.SINGER WHERE id = :singerId";
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("singerId", id);
        return getNamedParameterJdbcTemplate().queryForObject(sql,
                namedParameters, String.class);
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
    public void insert(Singer singer) {

    }

    @Override
    public void update(Singer singer) {

    }

    @Override
    public void delete(Long singerId) {

    }

    @Override
    public List<Singer> findAllWithDetail() {
        return null;
    }

    @Override
    public void insertWithDetail(Singer singer) {

    }

    @Override
    public void setDataSource(DataSource dataSource) {

    }

    public List<Singer> findAllWithAlbums() {
        String sql = "select s.ID, s.FIRST_NAME, s.LAST_NAME, s.BIRTH_DATE" +
                ", a.ID as album_id, a.TITLE, a.RELEASE_DATE from journal.SINGER s " +
                "left join journal.ALBUM a on s.ID = a.SINGER_ID";
        return namedParameterJdbcTemplate.query(sql, new SingerWithDetailExtractor());
    }



    @Override
    public void insertWithAlbum(Singer singer) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    private static final class SingerWithDetailExtractor  implements
            ResultSetExtractor<List<Singer>> {
        @Override
        public List<Singer> extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            Map<Long, Singer> map = new HashMap<>();
            Singer singer;
            while (rs.next())  {
                Long id = rs.getLong("id");
                singer = map.get(id);
                if (singer == null) {
                    singer = new Singer();
                    singer.setId(id);
                    singer.setFirstName(rs.getString("FIRST_NAME"));
                    singer.setLastName(rs.getString("LAST_NAME"));
                    singer.setBirthDate(rs.getDate("BIRTH_DATE"));
                    singer.setAlbums(new ArrayList<>());
                    map.put(id, singer);
                }
                Long albumId = rs.getLong("album_id");
                if (albumId > 0) {
                    Album album = new Album();
                    album.setId(albumId);
                    album.setSingerId(id);
                    album.setTitle(rs.getString("TITLE"));
                    album.setReleaseDate(rs.getDate("RELEASE_DATE"));
                    singer.addAbum(album);
                }
            }
            return new ArrayList<>(map.values());
        }
    }
}
