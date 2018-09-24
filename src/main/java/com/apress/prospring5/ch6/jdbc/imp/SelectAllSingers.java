package com.apress.prospring5.ch6.jdbc.imp;

import com.apress.prospring5.ch6.entities.Singer;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by simon on 21/09/18.
 */
//@Repository("sellectAllSinger")
public class SelectAllSingers extends MappingSqlQuery<Singer> {
    private static final String SQL_SELECT_ALL_SINGER =
            "select s.ID, s.FIRST_NAME, s.LAST_NAME, s.BIRTH_DATE from journal.SINGER s ";

   public SelectAllSingers(DataSource dataSource) {
        super(dataSource,
                SQL_SELECT_ALL_SINGER);
    }

    protected Singer mapRow(ResultSet rs, int rowNum)
            throws SQLException {
        Singer singer = new Singer();
        singer.setId(rs.getLong("ID"));
        singer.setFirstName(rs.getString("FIRST_NAME"));
        singer.setLastName(rs.getString("LAST_NAME"));
        singer.setBirthDate(rs.getDate("BIRTH_DATE"));
        return singer;
    }
}
