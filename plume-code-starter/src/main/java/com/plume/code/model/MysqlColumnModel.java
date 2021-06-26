package com.plume.code.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.sql.JDBCType;
import java.util.Set;

import static com.plume.code.common.helper.GeneratorHepler.removePrefix;
import static com.plume.code.common.helper.GeneratorHepler.removeUnderline;


@Data
public class MysqlColumnModel extends FieldModel {
    // 所属库
    private String tableSchema;

    // 所属表
    private String tableName;

    // 列名
    private String columnName;

    // bigint(11)，varchar(30)
    private String columnType;

    // bigint->Long，int->Integer，varchar/char->String，timestamp->Timestamp，date/time->Date，float->Float，double/decimal->Double，blob->byte[]
    private String dataType;

    // 列缺省值
    private Object columnDefault;

    // 列最大字节长度，utf-8：
    private Long characterOctetLength;

    // characterMaximumLength*3，gbk：characterMaximumLength* 2
    // 列最大长度
    private Long characterMaximumLength;

    // 字节顺序码，从1开始
    private Long ordinalPosition;

    // 是否可为空，YES：可空，NO：非空
    private String isNullable;

    // 列键值,PRI主键
    private String columnKey;

    // auto_increment 自增长
    private String extra;

    // 列描述
    private String columnComment;

    // 列权限集合，以逗号隔开
    private String privileges;
}
