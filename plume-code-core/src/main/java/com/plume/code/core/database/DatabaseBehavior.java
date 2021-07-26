package com.plume.code.core.database;

import com.plume.code.core.common.model.ConnectionModel;
import com.plume.code.core.common.model.SettingModel;
import com.plume.code.core.database.model.ClassModel;
import com.plume.code.core.database.model.FieldModel;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
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

    protected DataSource getDataSource() {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource(connectionModel.getUrl(), connectionModel.getUsername(), connectionModel.getPassword(), false);
        dataSource.setDriverClassName(connectionModel.getDriver());
        return dataSource;
    }

    @SneakyThrows
    protected DatabaseMetaData getDatabaseMetaData() {
        DataSource dataSource = getDataSource();
        Connection connection = dataSource.getConnection();
        return connection.getMetaData();
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
