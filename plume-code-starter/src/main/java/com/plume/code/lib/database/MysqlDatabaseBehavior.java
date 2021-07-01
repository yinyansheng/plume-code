package com.plume.code.lib.database;

import com.plume.code.common.constrant.DatabaseConstant;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.lib.database.model.FieldModel;
import com.plume.code.lib.database.model.MysqlColumnModel;
import com.plume.code.lib.database.model.MysqlTableModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.sql.JDBCType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.plume.code.common.helper.StringHelper.*;

/**
 * mysql database service implement
 *
 * @author yinyansheng
 */
@Component
@Scope(value = "prototype")
class MysqlDatabaseBehavior extends DatabaseBehavior {

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

    @Override
    public String getDatabaseName() {
        String schema = getJdbcTemplate().queryForObject(SCHEME_SQL, String.class);

        if (StringUtils.isEmpty(schema)) {
            throw new IllegalArgumentException("get schema failure");
        }

        return schema;
    }

    @Override
    public List<String> listTableName() {
        String schema = getDatabaseName();
        List<MysqlTableModel> tableModelList = getJdbcTemplate().query(TABLE_SQL, new BeanPropertyRowMapper<>(MysqlTableModel.class), schema);
        return tableModelList.stream().map(MysqlTableModel::getTableName).collect(Collectors.toList());
    }

    @Override
    public List<ClassModel> listClassModel(SettingModel settingModel) {
        String schema = getDatabaseName();
        List<MysqlTableModel> tableModelList = getJdbcTemplate().query(TABLE_SQL, new BeanPropertyRowMapper<>(MysqlTableModel.class), schema);
        return tableModelList.stream().map(r -> mapToClassModel(settingModel, r)).collect(Collectors.toList());
    }

    @Override
    public Set<String> getPrimaryKeySet(String tableName) {
        String schema = getDatabaseName();
        return new HashSet<>(getJdbcTemplate().queryForList(PRIMARY_KEY_SQL, String.class, schema, tableName));
    }

    @Override
    public List<FieldModel> listFieldModel(SettingModel settingModel, String tableName) {
        String schema = getDatabaseName();
        Set<String> primaryKeySet = getPrimaryKeySet(tableName);
        List<MysqlColumnModel> columnModelList = getJdbcTemplate().query(COLUMN_SQL, new BeanPropertyRowMapper<>(MysqlColumnModel.class), schema, tableName);
        return columnModelList.stream().map(r -> mapToFieldModel(settingModel, r, primaryKeySet)).collect(Collectors.toList());
    }

    private ClassModel mapToClassModel(SettingModel settingModel, MysqlTableModel mysqlTableModel) {
        ClassModel classModel = new ClassModel();

        String name = mysqlTableModel.getTableName().toLowerCase();
        if (StringUtils.isNotEmpty(settingModel.getTablePrefix()) && mysqlTableModel.getTableName().startsWith(settingModel.getTablePrefix())) {
            name = removePrefix(mysqlTableModel.getTableName(), settingModel.getTablePrefix().split(","));
        }
        classModel.setName(removeUnderline(name));
        classModel.setTableName(mysqlTableModel.getTableName());
        classModel.setComment(mysqlTableModel.getTableComment());
        return classModel;
    }

    private FieldModel mapToFieldModel(SettingModel settingModel, MysqlColumnModel mysqlColumnModel, Set<String> primaryKeySet) {
        FieldModel fieldModel = new FieldModel();

        String name = mysqlColumnModel.getColumnName().toLowerCase();
        if (StringUtils.isNotEmpty(settingModel.getColumnPrefix())) {
            name = removePrefix(name, settingModel.getColumnPrefix().split(","));
        }
        fieldModel.setName(removeUnderline(name));
        fieldModel.setUpperCaseName(upperFirstCase(fieldModel.getName()));
        fieldModel.setColumnName(mysqlColumnModel.getColumnName());

        fieldModel.setComment(mysqlColumnModel.getColumnComment());

        JDBCType jdbcType = getJdbcType(mysqlColumnModel.getDataType());
        fieldModel.setType(getFieldType(jdbcType));
        fieldModel.setJdbcType(jdbcType.getName());
        fieldModel.setValue(mysqlColumnModel.getColumnDefault());
        fieldModel.setPk(primaryKeySet.contains(mysqlColumnModel.getColumnName()));
        fieldModel.setMultiplePk(primaryKeySet.size() > 1);

        if (mysqlColumnModel.getExtra().contains("auto_increment")) {
            fieldModel.setPkStrategy(DatabaseConstant.PkStrategy.AUTO_INCREMENT);
        } else {
            fieldModel.setPkStrategy(DatabaseConstant.PkStrategy.NONE);
        }

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
