package com.plume.code.lib.database;

import com.plume.code.common.model.ConnectionModel;
import com.plume.code.common.model.SettingModel;
import com.plume.code.lib.database.model.ClassModel;
import com.plume.code.lib.database.model.FieldModel;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.JDBCType;
import java.util.List;
import java.util.Set;

/**
 * database service
 *
 * @author yinyansheng
 */
public abstract class DatabaseBehavior {
    protected ConnectionModel connectionModel;

    void initialize(ConnectionModel connectionModel) {
        if (null == connectionModel) {
            throw new IllegalArgumentException("connectionModel must be not null");
        }

        this.connectionModel = connectionModel;
    }

    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    private DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(connectionModel.getDriver());
        dataSource.setJdbcUrl(connectionModel.getUrl());
        dataSource.setUsername(connectionModel.getUsername());
        dataSource.setPassword(connectionModel.getPassword());
        return dataSource;
    }

    public abstract String getDatabaseName();

    public abstract List<String> listTableName();

    public abstract List<ClassModel> listClassModel(SettingModel settingModel);

    public abstract Set<String> getPrimaryKeySet(String tableName);

    public abstract List<FieldModel> listFieldModel(SettingModel settingModel, String tableName);

    protected String getFieldType(JDBCType jdbcType) {
        switch (jdbcType) {
            case BIT:
            case BOOLEAN:
                return "Boolean";
            case TINYINT:
            case SMALLINT:
            case INTEGER:
                return "Integer";
            case BIGINT:
                return "Long";
            case REAL:
                return "Float";
            case FLOAT:
            case DOUBLE:
                return "Double";
            case DECIMAL:
            case NUMERIC:
                return "BigDecimal";
            case TIMESTAMP:
                return "Timestamp";
            case VARCHAR:
            case CHAR:
            case NCHAR:
            case NVARCHAR:
            case LONGVARCHAR:
            case LONGNVARCHAR:
                return "String";
            case DATE:
            case TIME:
                return "Date";
            case CLOB:
            case NCLOB:
            case BLOB:
            case BINARY:
            case VARBINARY:
            case LONGVARBINARY:
                return "byte[]";
            default:
                return "Object";
        }
    }


}
