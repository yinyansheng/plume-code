package com.plume.code.service.impl;

import com.plume.code.model.*;
import com.plume.code.service.DatabaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.ArrayList;
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

    private MysqlDatabaseService(ConnectionModel connectionModel, SettingModel settingModel) {
        super(connectionModel, settingModel);
    }

    public static MysqlDatabaseService instance(ConnectionModel connectionModel, SettingModel settingModel) {
        return new MysqlDatabaseService(connectionModel, settingModel);
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
    public List<BaseTableModel> listTableModel() {
        String schema = getSchema();
        List<MysqlTableModel> tableModelList = getJdbcTemplate().query(TABLE_SQL, new BeanPropertyRowMapper<>(MysqlTableModel.class), schema);
        tableModelList.forEach(r -> r.initialize(settingModel));
        return new ArrayList<>(tableModelList);
    }

    @Override
    public List<BaseColumnModel> listColumnModel(String tableName) {
        String schema = getSchema();
        List<MysqlColumnModel> columnModelList = getJdbcTemplate().query(COLUMN_SQL, new BeanPropertyRowMapper<>(MysqlColumnModel.class), schema, tableName);
        columnModelList.forEach(r -> r.initialize(settingModel));
        return new ArrayList<>(columnModelList);
    }

}
