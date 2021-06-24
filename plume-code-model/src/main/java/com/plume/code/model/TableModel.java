package com.plume.code.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TableModel {
    private String tableSchema;
    private String tableName;
    private String tableComment;
    private Timestamp createTime;
}
