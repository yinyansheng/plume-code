package com.plume.code.service;

import com.plume.code.model.ColumnModel;
import com.plume.code.model.ConnectionModel;
import com.plume.code.model.TableModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * mysql database service implement
 *
 * @author yinyansheng
 */
public class MysqlDatabaseService extends DatabaseService {

    private static final String SCHEME_SQL = "SELECT database()";
    private static final String COLUMN_SQL = "SELECT DISTINCT * FROM information_schema.COLUMNS WHERE table_schema = ? AND table_name = ? ORDER BY ORDINAL_POSITION";
    private static final String TABLE_SQL = "SELECT TABLE_SCHEMA, TABLE_NAME, TABLE_COMMENT, CREATE_TIME FROM information_schema.tables WHERE table_schema = ?";

    private MysqlDatabaseService(ConnectionModel connectionModel) {
        super(connectionModel);
    }

    public static MysqlDatabaseService instance(ConnectionModel connectionModel) {
        return new MysqlDatabaseService(connectionModel);
    }

    @Override
    public String getSchema() {
        String schema = getJdbcTemplate().queryForObject(SCHEME_SQL, String.class);

        if (StringUtils.isEmpty(schema)) {
            throw new IllegalArgumentException("get schema failure");
        }

        return schema;
    }

    @Override
    public List<ColumnModel> listColumnModel(String tableName) {
        String schema = getSchema();
        return getJdbcTemplate().query(COLUMN_SQL, new BeanPropertyRowMapper<>(ColumnModel.class), schema, tableName);
    }

    @Override
    public List<TableModel> listTableModel() {
        String schema = getSchema();
        return getJdbcTemplate().query(TABLE_SQL, new BeanPropertyRowMapper<>(TableModel.class), schema);
    }
}
