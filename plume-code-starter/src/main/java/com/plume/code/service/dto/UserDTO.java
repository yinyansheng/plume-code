package com.plume.code.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true, fluent = true)
public class UserDTO {
    private String name;
    private Integer age;
    private Timestamp timestamp;


}
