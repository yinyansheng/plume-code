package com.plume.code.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectionModel {
    /**
     * 0:mysql
     * 1:h2
     */
    private Integer type;
    private String driver;
    private String url;
    private String username;
    private String password;
}
