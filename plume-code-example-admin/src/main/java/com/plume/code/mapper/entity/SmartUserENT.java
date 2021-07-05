package com.plume.code.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;

/**
 * @description: ${comment}
 * @author: plume-code
 * @date: 2021-07-05 11:13:50
 **/
@TableName("SMART_USER")
public class SmartUserENT implements Serializable {

    /**
     * columnName:ID
     * comment:
     */
    @TableId(value = "id")
    private Long id;

    /**
     * columnName:NAME
     * comment:
     */
    private String name;

    /**
     * columnName:PASSWORD
     * comment:
     */
    private String password;

    /**
     * columnName:VERSION
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


