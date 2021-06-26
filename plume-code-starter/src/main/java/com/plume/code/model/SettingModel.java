package com.plume.code.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingModel {
    private String batchNo;

    private String tablePrefix;

    private String columnPrefix;

    private String projectName;

    private String packageName;

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
     * 1:d2admin
     * 2:iview
     */
    private Integer portalMode;

    /**
     * 1：api
     * 2:admin
     * 3:api & admin
     */
    private Integer controllerMode;

    /**
     * 1:mybatis
     * 2:mybatis-plus
     * 3:jpa
     */
    private Integer repositoryMode;

}
