package com.plume.code.lib.database;

import com.plume.code.common.constrant.DatabaseConstant;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.lib.database.model.FieldModel;
import com.plume.code.lib.database.model.MetaDataColumnModel;
import com.plume.code.lib.database.model.MetaDataTableModel;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringUtils;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.plume.code.common.helper.StringHelper.*;

/**
 * DatabaseMetaData service implement
 *
 * @author yinyansheng
 */
abstract class MetaDataDatabaseBehavior extends DatabaseBehavior {

    protected abstract String getDatabaseNameSql();

    @Override
    public String getDatabaseName() {
        String databaseName = getJdbcTemplate().queryForObject(getDatabaseNameSql(), String.class);

        if (StringUtils.isEmpty(databaseName)) {
            throw new IllegalArgumentException("get schema failure");
        }

        return databaseName;
    }

    @Override
    @SneakyThrows
    public List<String> listTableName() {
        ResultSet tables = getDatabaseMetaData().getTables(null, null, null, new String[]{"TABLE"});
        BeanListHandler<MetaDataTableModel> bh = new BeanListHandler<>(MetaDataTableModel.class);
        List<MetaDataTableModel> tableModelList = bh.handle(tables);
        return tableModelList.stream().map(MetaDataTableModel::getTABLE_NAME).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public List<ClassModel> listClassModel(SettingModel settingModel) {
        ResultSet tables = getDatabaseMetaData().getTables(null, null, null, new String[]{"TABLE"});
        BeanListHandler<MetaDataTableModel> bh = new BeanListHandler<>(MetaDataTableModel.class);
        List<MetaDataTableModel> tableModelList = bh.handle(tables);

        return tableModelList.stream().map(r -> mapToClassModel(settingModel, r)).collect(Collectors.toList());
    }

    @Override
    @SneakyThrows
    public Set<String> getPrimaryKeySet(String tableName) {
        ResultSet resultSet = getDatabaseMetaData().getPrimaryKeys(null, null, tableName);
        Set<String> set = new HashSet<>();
        while (resultSet.next()) {
            set.add(resultSet.getString("COLUMN_NAME"));
        }
        return set;
    }

    @Override
    @SneakyThrows
    public List<FieldModel> listFieldModel(SettingModel settingModel, String tableName) {
        ResultSet resultSet = getDatabaseMetaData().getColumns(null, null, tableName, null);

        BeanListHandler<MetaDataColumnModel> bh = new BeanListHandler<>(MetaDataColumnModel.class);
        List<MetaDataColumnModel> tableModelList = bh.handle(resultSet);
        Set<String> primaryKeySet = getPrimaryKeySet(tableName);
        return tableModelList.stream().map(r -> mapToFieldModel(settingModel, r, primaryKeySet)).collect(Collectors.toList());
    }

    private ClassModel mapToClassModel(SettingModel settingModel, MetaDataTableModel metaDataTableModel) {
        ClassModel classModel = new ClassModel();

        String name = metaDataTableModel.getTABLE_NAME().toLowerCase();
        if (StringUtils.isNotEmpty(settingModel.getTablePrefix())) {
            name = removePrefix(metaDataTableModel.getTABLE_NAME(), settingModel.getTablePrefix().split(","));
        }
        classModel.setName(removeUnderline(name));
        classModel.setTableName(metaDataTableModel.getTABLE_NAME());
        classModel.setComment(metaDataTableModel.getREMARKS());
        return classModel;
    }

    private FieldModel mapToFieldModel(SettingModel settingModel, MetaDataColumnModel metaDataColumnModel, Set<String> primaryKeySet) {
        FieldModel fieldModel = new FieldModel();

        String name = metaDataColumnModel.getCOLUMN_NAME().toLowerCase();
        if (StringUtils.isNotEmpty(settingModel.getColumnPrefix())) {
            name = removePrefix(name, settingModel.getColumnPrefix().split(","));
        }
        fieldModel.setName(removeUnderline(name));
        fieldModel.setUpperCaseName(upperFirstCase(fieldModel.getName()));
        fieldModel.setColumnName(metaDataColumnModel.getCOLUMN_NAME());

        fieldModel.setComment(metaDataColumnModel.getREMARKS());

        JDBCType jdbcType = JDBCType.valueOf(metaDataColumnModel.getDATA_TYPE());
        fieldModel.setType(getFieldType(jdbcType));
        fieldModel.setJdbcType(jdbcType.getName());
        fieldModel.setValue(metaDataColumnModel.getCOLUMN_DEF());
        fieldModel.setPk(primaryKeySet.contains(metaDataColumnModel.getCOLUMN_NAME()));
        fieldModel.setMultiplePk(primaryKeySet.size() > 1);

        if (metaDataColumnModel.getIS_AUTOINCREMENT().contains("YES")) {
            fieldModel.setPkStrategy(DatabaseConstant.PkStrategy.AUTO_INCREMENT);
        } else {
            fieldModel.setPkStrategy(DatabaseConstant.PkStrategy.NONE);
        }

        return fieldModel;
    }
}
