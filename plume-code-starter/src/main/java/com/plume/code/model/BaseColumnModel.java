package com.plume.code.model;

import java.sql.JDBCType;
import java.util.Set;

/**
 * @author yinyansheng
 */
public abstract class BaseColumnModel {
    protected String fieldName;
    protected String fieldType;
    protected Object fieldValue;
    protected String fieldComment;
    protected boolean isPK;
    protected boolean isMultiplePk;

    /**
     * 0：null
     * 1:AUTO_INCREMENT
     * 2:UUID
     */
    protected Integer primaryKeyStrategy;

    public abstract void initialize(SettingModel settingModel, Set<String> primaryKeySet);

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
