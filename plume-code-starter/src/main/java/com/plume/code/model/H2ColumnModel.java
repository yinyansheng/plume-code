package com.plume.code.model;

import lombok.Data;


@Data
public class H2ColumnModel {
    // 所属库
    private String tableCatalog;

    // 所属表
    private String tableName;

    // 列名
    private String columnName;

    private String dataType;

    // 列缺省值
    private Object columnDefault;

    // 字节顺序码，从1开始
    private Long ordinalPosition;

    // 列描述
    private String remarks;

}
