package com.plume.code.service;

import com.plume.code.model.FieldModel;
import com.plume.code.model.ClassModel;
import com.plume.code.model.ConnectionModel;
import com.plume.code.model.SettingModel;
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
public abstract class DatabaseService {
    protected ConnectionModel connectionModel;

    protected SettingModel settingModel;

    protected DatabaseService(ConnectionModel connectionModel, SettingModel settingModel) {
        this.connectionModel = connectionModel;
        this.settingModel = settingModel;
    }
    
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }

    public DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(connectionModel.getDriver());
        dataSource.setJdbcUrl(connectionModel.getUrl());
        dataSource.setUsername(connectionModel.getUsername());
        dataSource.setPassword(connectionModel.getPassword());
        return dataSource;
    }

    public abstract String getDatabaseName();

    public abstract List<ClassModel> listTableModel();

    public abstract Set<String> getPrimaryKeySet(String tableName);

    public abstract List<FieldModel> listColumnModel(String tableName);

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
            case VARCHAR:
            case CHAR:
            case NCHAR:
            case NVARCHAR:
            case LONGVARCHAR:
            case LONGNVARCHAR:
                return "String";
            case DATE:
            case TIME:
            case TIMESTAMP:
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
