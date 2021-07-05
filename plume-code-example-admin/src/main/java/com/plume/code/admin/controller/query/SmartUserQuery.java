package com.plume.code.admin.controller.query;


import com.plume.code.common.model.BasePageQuery;

import java.io.Serializable;

/**
 * @description: ${comment}
 * @author: plume-code
 * @date: 2021-07-05 11:13:50
 **/
public class SmartUserQuery extends BasePageQuery implements Serializable {

    /**
     * comment:
     */
    private Long id;

    /**
     * comment:
     */
    private String name;

    /**
     * comment:
     */
    private String password;

    /**
     * comment:version
     */
    private Long version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}

