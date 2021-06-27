package com.plume.code.lib.database.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author yinyansheng
 * mysql table model
 */
@Data
public class MysqlTableModel {
    private String tableSchema;
    private String tableName;
    private String tableComment;
    private Timestamp createTime;
}
