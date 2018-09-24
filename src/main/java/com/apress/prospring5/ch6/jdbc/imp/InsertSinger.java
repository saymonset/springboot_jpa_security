package com.apress.prospring5.ch6.jdbc.imp;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Created by simon on 24/09/18.
 */
public class InsertSinger  extends SqlUpdate {
    private static final String SQL_INSERT_SINGER =
            "insert into journal.SINGER (FIRST_NAME, LAST_NAME, BIRTH_DATE)  VALUES(:FIRST_NAME, :LAST_NAME, :BIRTH_DATE )  ";






    public InsertSinger(DataSource dataSource)  {
        super(dataSource, SQL_INSERT_SINGER);
        super.declareParameter(new SqlParameter("FIRST_NAME", Types.VARCHAR));
        super.declareParameter(new SqlParameter("LAST_NAME", Types.VARCHAR));
        super.declareParameter(new SqlParameter("BIRTH_DATE", Types.DATE));
        super.setGeneratedKeysColumnNames(new String ("ID"));
        super.setReturnGeneratedKeys(true);
    }
}