package com.plume.code.service.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import java.io.Serializable;

/**
 * @description: 学生表
 * @author: yinyansheng
 * @date: 2021-06-29 16:58:50
 **/
@TableName("test_student")
public class StudentENT implements Serializable {

    /**
     * columnName:id
     * comment:自动增长ip
     */

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * columnName:school_number
     * comment:学号
     */
    private String schoolNumber;

    /**
     * columnName:name
     * comment:姓名
     */
    private String name;

    /**
     * columnName:classes
     * comment:班级
     */
    private String classes;

    /**
     * columnName:hobby
     * comment:个人爱好
     */
    private String hobby;

    /**
     * columnName:create_time
     * comment:入学时间
     */
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}

