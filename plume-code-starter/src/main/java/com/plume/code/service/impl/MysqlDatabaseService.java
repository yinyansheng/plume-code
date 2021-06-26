package com.plume.code.service.impl;

import com.plume.code.model.*;
import com.plume.code.service.DatabaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.sql.JDBCType;
import java.util.ArrayList;
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
public class MysqlDatabaseService extends DatabaseService {

    private static final String SCHEME_SQL = "SELECT database()";
    private static final String COLUMN_SQL = "SELECT DISTINCT * FROM information_schema.COLUMNS WHERE table_schema = ? AND table_name = ? ORDER BY ORDINAL_POSITION";
    private static final String TABLE_SQL = "SELECT TABLE_SCHEMA, TABLE_NAME, TABLE_COMMENT, CREATE_TIME FROM information_schema.tables WHERE table_schema = ?";
    private static final String PRIMARY_KEY_SQL = "SELECT k.column_name\n" +
            "FROM information_schema.table_constraints t\n" +
            "JOIN information_schema.key_column_usage k\n" +
            "USING (constraint_name,table_schema,table_name)\n" +
            "WHERE t.constraint_type='PRIMARY KEY'\n" +
            "AND t.table_schema=?\n" +
            "AND t.table_name=?";

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
    public List<ClassModel> listTableModel() {
        String schema = getSchema();
        List<MysqlTableModel> tableModelList = getJdbcTemplate().query(TABLE_SQL, new BeanPropertyRowMapper<>(MysqlTableModel.class), schema);
        return tableModelList.stream().map(r -> mapToClassModel(r)).collect(Collectors.toList());
    }

    @Override
    public Set<String> getPrimaryKeySet(String tableName) {
        String schema = getSchema();
        return new HashSet<>(getJdbcTemplate().query(PRIMARY_KEY_SQL, new BeanPropertyRowMapper<>(String.class), schema, tableName));
    }

    @Override
    public List<FieldModel> listColumnModel(String tableName) {
        String schema = getSchema();
        Set<String> primaryKeySet = getPrimaryKeySet(tableName);
        List<MysqlColumnModel> columnModelList = getJdbcTemplate().query(COLUMN_SQL, new BeanPropertyRowMapper<>(MysqlColumnModel.class), schema, tableName);
        return columnModelList.stream().map(r -> mapToFieldModel(r, primaryKeySet)).collect(Collectors.toList());
    }

    public ClassModel mapToClassModel(MysqlTableModel mysqlTableModel) {
        ClassModel classModel = new ClassModel();

        String name = mysqlTableModel.getTableName().toLowerCase();
        if (StringUtils.isNotEmpty(settingModel.getTablePrefix()) && mysqlTableModel.getTableName().startsWith(settingModel.getTablePrefix())) {
            name = removePrefix(mysqlTableModel.getTableName(), settingModel.getTablePrefix().split(","));
        }
        classModel.setName(removeUnderline(name));

        classModel.setName(removeUnderline(classModel.getName()));
        classModel.setComment(mysqlTableModel.getTableComment());
        return classModel;
    }

    private FieldModel mapToFieldModel(MysqlColumnModel mysqlColumnModel, Set<String> primaryKeySet) {
        FieldModel fieldModel = new FieldModel();

        String name = mysqlColumnModel.getColumnName().toLowerCase();
        if (StringUtils.isNotEmpty(settingModel.getColumnPrefix())) {
            name = removePrefix(name, settingModel.getColumnPrefix().split(","));
        }
        fieldModel.setName(removeUnderline(name));

        fieldModel.setComment(mysqlColumnModel.getColumnComment());

        JDBCType jdbcType = getJdbcType(mysqlColumnModel.getDataType());
        fieldModel.setType(getFieldType(jdbcType));

        fieldModel.setValue(mysqlColumnModel.getColumnDefault());
        fieldModel.setPk(primaryKeySet.contains(mysqlColumnModel.getColumnName()));
        fieldModel.setMultiplePk(primaryKeySet.size() > 1);
        fieldModel.setPkStrategy(0);
        return fieldModel;
    }

    private JDBCType getJdbcType(String dataType) {
        switch (dataType.toLowerCase()) {
            case "bit":
                return JDBCType.BIT;
            case "tinyint":
            case "smallint":
            case "mediumint":
            case "int":
            case "integer":
                return JDBCType.INTEGER;
            case "bigint":
                return JDBCType.BIGINT;
            case "double":
            case "decimal":
                return JDBCType.DECIMAL;
            case "date":
            case "datetime":
                return JDBCType.DATE;
            case "timestamp":
                return JDBCType.TIMESTAMP;
            default:
                return JDBCType.VARCHAR;
        }
    }
}
