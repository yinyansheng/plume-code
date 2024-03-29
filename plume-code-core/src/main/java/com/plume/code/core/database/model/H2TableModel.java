package com.plume.code.core.database.model;

import lombok.Data;

/**
 * @author yinyansheng
 * mysql table model
 */
@Data
public class H2TableModel {
    private String tableCatalog;
    private String tableName;
    private String remarks;
}
