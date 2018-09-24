package com.apress.prospring5.ch6.jdbc.imp;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;
import java.sql.Types;
import javax.sql.DataSource;
/**
 * Created by simon on 24/09/18.
 */
public class InsertSingerAlbum extends BatchSqlUpdate {
    private static final String SQL_INSERT_SINGER_ALBUM =
            "insert into ALBUM (SINGER_ID, TITLE, RELEASE_DATE)" +
                    "    values (:SINGER_ID, :TITLE, :RELEASE_DATE)";




    private static final int BATCH_SIZE =  10;
    public InsertSingerAlbum(DataSource dataSource) {
        super(dataSource, SQL_INSERT_SINGER_ALBUM);
        declareParameter(new SqlParameter("SINGER_ID", Types.INTEGER));
        declareParameter(new SqlParameter("TITLE", Types.VARCHAR));
        declareParameter(new SqlParameter("RELEASE_DATE", Types.DATE));
        setBatchSize(BATCH_SIZE);
    }
}
