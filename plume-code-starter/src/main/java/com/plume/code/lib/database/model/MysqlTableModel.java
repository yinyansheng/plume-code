package com.plume.code.lib.database.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

import static com.plume.code.common.helper.GeneratorHepler.removePrefix;
import static com.plume.code.common.helper.GeneratorHepler.removeUnderline;

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
