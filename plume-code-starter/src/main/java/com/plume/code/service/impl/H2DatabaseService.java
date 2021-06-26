package com.plume.code.service.impl;

import com.plume.code.model.*;
import com.plume.code.service.DatabaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.JDBCType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.plume.code.common.helper.GeneratorHepler.removePrefix;
import static com.plume.code.common.helper.GeneratorHepler.removeUnderline;

/**
 * mysql database service implement
 *
 * @author yinyansheng
 */
public class H2DatabaseService extends DatabaseService {

    private static final String SCHEME_SQL = "SELECT database()";

    private static final String TABLE_SQL = "SELECT * FROM INFORMATION_SCHEMA.TABLES " +
            "WHERE TABLE_TYPE='TABLE' AND TABLE_CATALOG=?";

    private static final String COLUMN_SQL = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE TABLE_CATALOG=? AND TABLE_NAME=? ORDER BY ORDINAL_POSITION";

    private static final String PRIMARY_KEY_SQL = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.INDEXES " +
            "WHERE TABLE_CATALOG=? AND TABLE_NAME=? AND PRIMARY_KEY='TRUE'";

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
    public List<ClassModel> listTableModel() {
        String schema = getSchema();
        List<H2TableModel> tableModelList = getJdbcTemplate().query(TABLE_SQL, new BeanPropertyRowMapper<>(H2TableModel.class), schema);
        return tableModelList.stream().map(this::mapToClassModel).collect(Collectors.toList());
    }

    @Override
    public Set<String> getPrimaryKeySet(String tableName) {
        String schema = getSchema();
        return new HashSet<>(getJdbcTemplate().queryForList(PRIMARY_KEY_SQL, String.class, schema, tableName));
    }

    @Override
    public List<FieldModel> listColumnModel(String tableName) {
        String schema = getSchema();
        Set<String> primaryKeySet = getPrimaryKeySet(tableName);
        List<H2ColumnModel> columnModelList = getJdbcTemplate().query(COLUMN_SQL, new BeanPropertyRowMapper<>(H2ColumnModel.class), schema, tableName);
        return columnModelList.stream().map(r -> mapToFieldModel(r, primaryKeySet)).collect(Collectors.toList());
    }

    public ClassModel mapToClassModel(H2TableModel h2TableModel) {
        ClassModel classModel = new ClassModel();

        String name = h2TableModel.getTableName().toLowerCase();
        if (StringUtils.isNotEmpty(settingModel.getTablePrefix()) && h2TableModel.getTableName().startsWith(settingModel.getTablePrefix())) {
            name = removePrefix(h2TableModel.getTableName(), settingModel.getTablePrefix().split(","));
        }
        classModel.setName(removeUnderline(name));

        classModel.setComment(h2TableModel.getRemarks());
        return classModel;
    }

    private FieldModel mapToFieldModel(H2ColumnModel h2ColumnModel, Set<String> primaryKeySet) {
        FieldModel fieldModel = new FieldModel();

        String name = h2ColumnModel.getColumnName().toLowerCase();
        if (StringUtils.isNotEmpty(settingModel.getColumnPrefix())) {
            name = removePrefix(name, settingModel.getColumnPrefix().split(","));
        }
        fieldModel.setName(removeUnderline(name));

        fieldModel.setComment(h2ColumnModel.getRemarks());

        JDBCType jdbcType = JDBCType.valueOf(Integer.parseInt(h2ColumnModel.getDataType()));
        fieldModel.setType(getFieldType(jdbcType));

        fieldModel.setValue(h2ColumnModel.getColumnDefault());
        fieldModel.setPk(primaryKeySet.contains(h2ColumnModel.getColumnName()));
        fieldModel.setMultiplePk(primaryKeySet.size() > 1);
        fieldModel.setPkStrategy(0);
        return fieldModel;
    }

}
