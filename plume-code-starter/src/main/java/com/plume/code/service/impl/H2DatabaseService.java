package com.plume.code.service.impl;

import com.plume.code.model.*;
import com.plume.code.service.DatabaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * mysql database service implement
 *
 * @author yinyansheng
 */
public class H2DatabaseService extends DatabaseService {

    private static final String SCHEME_SQL = "SELECT database()";
    private static final String COLUMN_SQL = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS where TABLE_CATALOG=? AND TABLE_NAME=? ORDER BY ORDINAL_POSITION";
    private static final String TABLE_SQL = "SELECT * FROM INFORMATION_SCHEMA.TABLES where TABLE_TYPE='TABLE' AND TABLE_CATALOG=?";
    private static final String PRIMARY_KEY_SQL = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.INDEXES where TABLE_CATALOG=? AND TABLE_NAME=? AND INDEX_TYPE_NAME='PRIMARY KEY'";

    private H2DatabaseService(ConnectionModel connectionModel, SettingModel settingModel) {
        super(connectionModel, settingModel);
    }

    public static H2DatabaseService instance(ConnectionModel connectionModel, SettingModel settingModel) {
        return new H2DatabaseService(connectionModel, settingModel);
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
        List<H2TableModel> tableModelList = getJdbcTemplate().query(TABLE_SQL, new BeanPropertyRowMapper<>(H2TableModel.class), schema);
        tableModelList.forEach(r -> r.initialize(settingModel));
        return new ArrayList<>(tableModelList);
    }

    @Override
    public Set<String> getPrimaryKeySet(String tableName) {
        String schema = getSchema();
        return new HashSet<>(getJdbcTemplate().queryForList(PRIMARY_KEY_SQL, String.class, schema, tableName));
    }

    @Override
    public List<BaseColumnModel> listColumnModel(String tableName) {
        String schema = getSchema();
        Set<String> primaryKeySet = getPrimaryKeySet(tableName);
        List<H2ColumnModel> columnModelList = getJdbcTemplate().query(COLUMN_SQL, new BeanPropertyRowMapper<>(H2ColumnModel.class), schema, tableName);
        columnModelList.forEach(r -> r.initialize(settingModel, primaryKeySet));
        return new ArrayList<>(columnModelList);
    }

}
