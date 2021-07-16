package com.plume.code.lib.database.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author yinyansheng
 * mysql table model
 */
@Data
public class MysqlTableModel {
    private String tableName;
    private String tableComment;
}
