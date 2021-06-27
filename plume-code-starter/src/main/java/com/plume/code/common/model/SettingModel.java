package com.plume.code.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
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
     * 0:none
     * 1:d2admin
     * 2:iview
     */
    private Integer portalMode;

    /**
     * 0:none
     * 1：api
     * 2:admin
     * 3:api & admin
     */
    private Integer controllerMode;

    /**
     * 0:none
     * 1：service
     */
    private Integer serviceMode;

    /**
     * 0:none
     * 1:mybatis
     * 2:mybatis-plus
     * 3:jpa
     * 4:hibernate
     */
    private Integer repositoryMode;

}
