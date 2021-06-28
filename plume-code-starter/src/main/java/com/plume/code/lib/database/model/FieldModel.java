package com.plume.code.lib.database.model;

import lombok.Data;

/**
 * @author yinyansheng
 */
@Data
public class FieldModel {
    private String columnName;
    private String name;
    private String type;
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
