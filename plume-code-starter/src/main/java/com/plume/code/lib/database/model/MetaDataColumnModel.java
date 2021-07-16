package com.plume.code.lib.database.model;

import lombok.Data;

@Data
public class MetaDataColumnModel {
    private String COLUMN_NAME;
    private Integer DATA_TYPE;
    private String TYPE_NAME;
    private Integer NULLABLE;
    private String REMARKS;
    private String ORDINAL_POSITION;
    private String IS_AUTOINCREMENT;
    private String COLUMN_DEF;
}
