package com.plume.code.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.sql.JDBCType;
import java.util.Set;

import static com.plume.code.common.helper.GeneratorHepler.removePrefix;
import static com.plume.code.common.helper.GeneratorHepler.removeUnderline;


@Data
public class H2ColumnModel extends BaseColumnModel {
    // 所属库
    private String tableSchema;

    // 所属表
    private String tableName;

    // 列名
    private String columnName;

    private String dataType;

    // 列缺省值
    private Object columnDefault;

    // 字节顺序码，从1开始
    private Long ordinalPosition;

    // 是否可为空，YES：可空，NO：非空
    private String isNullable;

    // auto_increment 自增长
    private String extra;

    // 列描述
    private String columnComment;


    @Override
    public void initialize(SettingModel settingModel, Set<String> primaryKeySet) {
        if (StringUtils.isNotEmpty(settingModel.getColumnPrefix())) {
            this.fieldName = removePrefix(columnName, settingModel.getColumnPrefix().split(","));
        }
        this.fieldName = removeUnderline(this.fieldName).toLowerCase();

        this.fieldComment = columnComment;

        JDBCType jdbcType = JDBCType.valueOf(Integer.parseInt(dataType));
        this.fieldType = getFieldType(jdbcType);

        this.fieldValue = columnDefault;
        this.isPK = primaryKeySet.contains(columnName);
        this.isMultiplePk = primaryKeySet.size() > 1;
    }
}
