package com.plume.code.lib.database;

import com.plume.code.common.constrant.DatabaseConstant;
import com.plume.code.lib.database.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.sql.JDBCType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.plume.code.common.helper.GeneratorHelper.removePrefix;
import static com.plume.code.common.helper.GeneratorHelper.removeUnderline;

/**
 * mysql database service implement
 *
 * @author yinyansheng
 */
@Component
@Scope("prototype")
class H2DatabaseBehavior extends DatabaseBehavior {

    private static final String SCHEME_SQL = "SELECT database()";

    private static final String TABLE_SQL = "SELECT * FROM INFORMATION_SCHEMA.TABLES " +
            "WHERE TABLE_TYPE='TABLE' AND TABLE_CATALOG=?";

    private static final String COLUMN_SQL = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE TABLE_CATALOG=? AND TABLE_NAME=? ORDER BY ORDINAL_POSITION";

    private static final String PRIMARY_KEY_SQL = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.INDEXES " +
            "WHERE TABLE_CATALOG=? AND TABLE_NAME=? AND PRIMARY_KEY='TRUE'";

    @Override
    public String getDatabaseName() {
        String schema = getJdbcTemplate().queryForObject(SCHEME_SQL, String.class);

        if (StringUtils.isEmpty(schema)) {
            throw new IllegalArgumentException("get schema failure");
        }

        return schema;
    }

    @Override
    public List<ClassModel> listTableModel() {
        String schema = getDatabaseName();
        List<H2TableModel> tableModelList = getJdbcTemplate().query(TABLE_SQL, new BeanPropertyRowMapper<>(H2TableModel.class), schema);
        return tableModelList.stream().map(this::mapToClassModel).collect(Collectors.toList());
    }

    @Override
    public Set<String> getPrimaryKeySet(String tableName) {
        String schema = getDatabaseName();
        return new HashSet<>(getJdbcTemplate().queryForList(PRIMARY_KEY_SQL, String.class, schema, tableName));
    }

    @Override
    public List<FieldModel> listFieldModel(String tableName) {
        String schema = getDatabaseName();
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
        classModel.setTableName(h2TableModel.getTableName());
        classModel.setComment(h2TableModel.getRemarks());
        return classModel;
    }

    private FieldModel mapToFieldModel(H2ColumnModel h2ColumnModel, Set<String> primaryKeySet) {
        FieldModel fieldModel = new FieldModel();

        String name = h2ColumnModel.getColumnName().toLowerCase();
        if (StringUtils.isNotEmpty(settingModel.getColumnPrefix())) {
            name = removePrefix(name, settingModel.getColumnPrefix().split(","));
        }
        fieldModel.setColumnName(h2ColumnModel.getColumnName());
        fieldModel.setName(removeUnderline(name));

        fieldModel.setComment(h2ColumnModel.getRemarks());

        JDBCType jdbcType = JDBCType.valueOf(Integer.parseInt(h2ColumnModel.getDataType()));
        fieldModel.setType(getFieldType(jdbcType));

        fieldModel.setValue(h2ColumnModel.getColumnDefault());
        fieldModel.setPk(primaryKeySet.contains(h2ColumnModel.getColumnName()));
        fieldModel.setMultiplePk(primaryKeySet.size() > 1);
        fieldModel.setPkStrategy(DatabaseConstant.PkStrategy.NONE);
        return fieldModel;
    }

}
