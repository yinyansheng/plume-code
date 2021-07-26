package com.plume.code.core.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingModel {
    private String batchNo;

    private String tablePrefix;

    private String columnPrefix;

    private String projectName;

    private String basePackageName;

    private Set<String> tableNameSet;

    private String author;

    /**
     * 0：关闭
     * 1：打开
     */
    private Boolean swaggerState;

    /**
     * 0：关闭
     * 1：打开
     */
    private Boolean lombokState;


    /**
     * 模板文件列表
     */
    private Set<String> templateNameSet;

    /**
     * query 对象后缀
     */
    private String queryPostfix = "Query";

    /**
     * vo 对象后缀
     */
    private String voPostfix = "VO";

    /**
     * dto 对象后缀
     */
    private String dtoPostfix = "DTO";

    /**
     * ent 对象后缀
     */
    private String entPostfix = "ENT";
}
