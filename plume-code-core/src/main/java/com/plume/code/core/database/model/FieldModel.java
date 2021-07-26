package com.plume.code.core.database.model;

import lombok.Data;

/**
 * @author yinyansheng
 */
@Data
public class FieldModel {
    private String columnName;
    private String name;
    private String type;
    private String jdbcType;
    private String upperCaseName;
    private Object value;
    private String comment;
    private boolean pk;
    private boolean multiplePk;

    /**
     * 0：NONE
     * 1:AUTO_INCREMENT
     * 2:UUID
     */
    private Integer pkStrategy;


}
