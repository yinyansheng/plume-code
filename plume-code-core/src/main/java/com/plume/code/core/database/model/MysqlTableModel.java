package com.plume.code.core.database.model;

import lombok.Data;

/**
 * @author yinyansheng
 * mysql table model
 */
@Data
public class MysqlTableModel {
    private String tableName;
    private String tableComment;
}
